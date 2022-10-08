package theEphemeral;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theEphemeral.actions.PlayTopCardWithCopiesAction;
import theEphemeral.cards.AbstractDefaultCard;
import theEphemeral.characters.TheEphemeral;
import theEphemeral.potions.ElysianNectar;
import theEphemeral.potions.PotionOfProphecy;
import theEphemeral.potions.TemporalTonic;
import theEphemeral.powers.HarbingerFormPower;
import theEphemeral.previewWidget.PreviewWidget;
import theEphemeral.relics.*;
import theEphemeral.util.IDCheckDontTouchPls;
import theEphemeral.util.TextureLoader;
import theEphemeral.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theEphemeral" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 4 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theEphemeral:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theEphemeral", and change to "yourmodname" rather than "thedefault".
// You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories, and press alt+c to make the replace case sensitive (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class EphemeralMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(EphemeralMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theEphemeralDefaultSettings = new Properties();
    public static final String ENABLE_VISION_SETTINGS = "enableVision";
    public static final String REVERSE_PREVIEWS_SETTINGS = "reversePreviews";
    public static boolean enableVision = true;
    public static boolean reversePreviews = false;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Ephemeral Mod";
    private static final String AUTHOR = "ResponsibleAlex";
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color EPHEMERAL_PURPLE = CardHelper.getColor(136.0f, 61.0f, 182.0f);
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "theEphemeralResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "theEphemeralResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "theEphemeralResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "theEphemeralResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "theEphemeralResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "theEphemeralResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "theEphemeralResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "theEphemeralResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "theEphemeralResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "theEphemeralResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theEphemeralResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theEphemeralResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theEphemeralResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theEphemeralResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theEphemeralResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theEphemeralResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theEphemeralResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEffectPath(String resourcePath) {
        return modID + "Resources/images/vfx/" + resourcePath;
    }

    @SuppressWarnings({"unused"})
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public EphemeralMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("theEphemeral");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename theEphemeralResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project) and press alt+c (or mark the match case option)
        // replace all instances of theEphemeral with yourModID, and all instances of thedefault with yourmodid (the same but all lowercase).
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        // It's important that the mod ID prefix for keywords used in the cards descriptions is lowercase!

        // 3. Scroll down (or search for "ADD CARDS") till you reach the ADD CARDS section, and follow the TODO instructions

        // 4. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than theEphemeral. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE.toString());
        
        BaseMod.addColor(TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE, EPHEMERAL_PURPLE, EPHEMERAL_PURPLE, EPHEMERAL_PURPLE,
                EPHEMERAL_PURPLE, EPHEMERAL_PURPLE, EPHEMERAL_PURPLE, EPHEMERAL_PURPLE,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        theEphemeralDefaultSettings.setProperty(ENABLE_VISION_SETTINGS, "FALSE");
        theEphemeralDefaultSettings.setProperty(REVERSE_PREVIEWS_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("ephemeralMod", "theEphemeralConfig", theEphemeralDefaultSettings);
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enableVision = config.getBool(ENABLE_VISION_SETTINGS);
            reversePreviews = config.getBool(REVERSE_PREVIEWS_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    @SuppressWarnings("ConstantConditions")
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = EphemeralMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    @SuppressWarnings("ConstantConditions")
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = EphemeralMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = EphemeralMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        EphemeralMod ephemeralMod = new EphemeralMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheEphemeral.Enums.THE_EPHEMERAL.toString());
        
        BaseMod.addCharacter(new TheEphemeral("the Default", TheEphemeral.Enums.THE_EPHEMERAL),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheEphemeral.Enums.THE_EPHEMERAL);
        
        receiveEditPotions();
        logger.info("Added " + TheEphemeral.Enums.THE_EPHEMERAL.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableVisionButton = new ModLabeledToggleButton(TheEphemeral.characterStrings.TEXT[3],
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enableVision, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enableVision = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("ephemeralMod", "theEphemeralConfig", theEphemeralDefaultSettings);
                config.setBool(ENABLE_VISION_SETTINGS, enableVision);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        ModLabeledToggleButton reversePreviewsButton = new ModLabeledToggleButton(TheEphemeral.characterStrings.TEXT[4],
                350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                reversePreviews, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:

            reversePreviews = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("ephemeralMod", "theEphemeralConfig", theEphemeralDefaultSettings);
                config.setBool(REVERSE_PREVIEWS_SETTINGS, reversePreviews);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(enableVisionButton);
        settingsPanel.addUIElement(reversePreviewsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    // ================ ADD POTIONS ===================

    // Potion Colors in RGB
    public static final Color NECTAR_YELLOW = CardHelper.getColor(255.0f, 225.0f, 0.0f); // Gold

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        BaseMod.addPotion(TemporalTonic.class, EPHEMERAL_PURPLE, EPHEMERAL_PURPLE, EPHEMERAL_PURPLE, TemporalTonic.POTION_ID, TheEphemeral.Enums.THE_EPHEMERAL);
        BaseMod.addPotion(ElysianNectar.class, NECTAR_YELLOW, NECTAR_YELLOW, null, ElysianNectar.POTION_ID, TheEphemeral.Enums.THE_EPHEMERAL);
        BaseMod.addPotion(PotionOfProphecy.class, Color.WHITE, Color.WHITE, NECTAR_YELLOW, PotionOfProphecy.POTION_ID, TheEphemeral.Enums.THE_EPHEMERAL);

        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        addRelic(new Laurel());
        addRelic(new Pincushion());
        addRelic(new LinenVeil());
        addRelic(new RitualAsh());
        addRelic(new GlowingFeather());
        addRelic(new CuriousTalon());
        addRelic(new HookAndYarn());
        addRelic(new Pomegranate());
        addRelic(new AncientScroll());
        addRelic(new Basin());
        addRelic(new StoneTablet());

        logger.info("Done adding relics!");
    }

    private void addRelic(AbstractRelic r) {
        BaseMod.addRelicToCustomPool(r, TheEphemeral.Enums.COLOR_EPHEMERAL_PURPLE);
        UnlockTracker.markRelicAsSeen(r.relicId);
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");

        new AutoAdd("EphemeralMod") // ${project.artifactId}
            .packageFilter(AbstractDefaultCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
            .setDefaultSeen(true)
            .cards();

        logger.info("Done adding cards!");
    }
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        String lang = "eng";
        if (Settings.GameLanguage.ZHS == Settings.language) {
            lang = "zhs";
        } else if (Settings.GameLanguage.KOR == Settings.language) {
            lang = "kor";
        }
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/" + lang + "/EphemeralMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/" + lang + "/EphemeralMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/" + lang + "/EphemeralMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/" + lang + "/EphemeralMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/" + lang + "/EphemeralMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/" + lang + "/EphemeralMod-Character-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {

        String lang = "eng";
        if (Settings.GameLanguage.ZHS == Settings.language) {
            lang = "zhs";
        } else if (Settings.GameLanguage.KOR == Settings.language) {
            lang = "kor";
        }

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/" + lang + "/EphemeralMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }


    // =========================================================

    public static int fatedThisTurn = 0;
    public static boolean preBattle = true;

    public static void startOfTurn() {
        isInHarbinger = false;
        fatedThisTurn = 0;
        PreviewWidget.StartOfTurn();
    }

    public static void startOfTurnPostOrbs() {
        PreviewWidget.StartOfTurnAccounting();
    }

    public static void preBattlePrepPrefix() {
        preBattle = true;
        PreviewWidget.StartOfCombat();
    }

    public static void preBattlePrepPostfix() {
        preBattle = false;
    }

    public static void endOfCombat() {
        PreviewWidget.EndOfCombat();

        if (AbstractDungeon.player != null
            && AbstractDungeon.player.hasRelic(GlowingFeather.ID)) {
            GlowingFeather feather = (GlowingFeather)AbstractDungeon.player.getRelic(GlowingFeather.ID);
            feather.Deactivate();
        }
    }

    public static void combatUpdate() {
        PreviewWidget.Update();
    }

    public static void renderPreviewWidget(SpriteBatch sb) {
        PreviewWidget.Render(sb);
    }

    public static boolean isInHarbinger = false;

    public static void atEndOfTurn() {
        if (AbstractDungeon.player.hasPower(HarbingerFormPower.POWER_ID)) {
            isInHarbinger = true;

            int amount = AbstractDungeon.player.getPower(HarbingerFormPower.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new PlayTopCardWithCopiesAction(amount - 1));
        }
    }


}
