package com.valoux.helper;

import java.util.HashMap;
import java.util.Map;

import com.valoux.constant.CommonConstants;

/**
 * This java class ItemComponentHelper use to perform all our service populate for
 * the collections.
 * 
 * @author Paparamjeetsingh Saluja
 * 
 */
public class ItemComponentHelper {
	
	public static Map<Integer, String> DIAMOND_CERTIFICATE_NAME_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_CERTIFICATE_NAME_MAP.put(1, "AGS");
		DIAMOND_CERTIFICATE_NAME_MAP.put(2, "EGL");
		DIAMOND_CERTIFICATE_NAME_MAP.put(3, "GIA");
	}
	
	public static Map<Integer, String> METAL_NAME_MAP = new HashMap<Integer, String>();
	
	static {
		METAL_NAME_MAP.put(1, "Gold");
		METAL_NAME_MAP.put(2, "Silver");
		METAL_NAME_MAP.put(3, "Platinum");
		METAL_NAME_MAP.put(4, "Rhodium");
		METAL_NAME_MAP.put(5, "Palladium");
		METAL_NAME_MAP.put(6, "Titanium");
		METAL_NAME_MAP.put(7, "Tungsten");
		METAL_NAME_MAP.put(8, "StainlessSteel");
		METAL_NAME_MAP.put(9, "GoldFilled");
		METAL_NAME_MAP.put(10, "GoldPlated");
		METAL_NAME_MAP.put(11, "RhodiumPlated");
		METAL_NAME_MAP.put(12, "Other");
	}
	
	public static Map<Integer, String> DIAMOND_CLARITY_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_CLARITY_MAP.put(1, "Flawless");
		DIAMOND_CLARITY_MAP.put(2, "Internally Flawless");
		DIAMOND_CLARITY_MAP.put(3, "VVS1");
		DIAMOND_CLARITY_MAP.put(4, "VVS2");
		DIAMOND_CLARITY_MAP.put(5, "VS1");
		DIAMOND_CLARITY_MAP.put(6, "VS2");
		DIAMOND_CLARITY_MAP.put(7, "Si1");
		DIAMOND_CLARITY_MAP.put(8, "Si2");
		DIAMOND_CLARITY_MAP.put(9, "Si3");
		DIAMOND_CLARITY_MAP.put(10, "I1");
		DIAMOND_CLARITY_MAP.put(11, "I2");
		DIAMOND_CLARITY_MAP.put(12, "I3");
		DIAMOND_CLARITY_MAP.put(13, "Opaque");
	}
	
	public static Map<Integer, String> METAL_GOLD_COLOR_MAP = new HashMap<Integer, String>();
	
	static {
		METAL_GOLD_COLOR_MAP.put(1, "Yellow");
		METAL_GOLD_COLOR_MAP.put(2, "White");
		METAL_GOLD_COLOR_MAP.put(3, "White Gold With Black Rhodium");
		METAL_GOLD_COLOR_MAP.put(4, "Rose");
		METAL_GOLD_COLOR_MAP.put(5, "Vermeil");
		METAL_GOLD_COLOR_MAP.put(6, "Two Tone");
		METAL_GOLD_COLOR_MAP.put(7, "Tri Color");
	}
	
	public static Map<Integer, String> DIAMOND_WEIGHT_MEASURE_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_WEIGHT_MEASURE_MAP.put(1, "Actual");
		DIAMOND_WEIGHT_MEASURE_MAP.put(2, "Estimated");
	}
	
	public static Map<Integer, String> PEARL_STYLE_MAP = new HashMap<Integer, String>();
	
	static {
		PEARL_STYLE_MAP.put(1, "Single");
		PEARL_STYLE_MAP.put(2, "Double");
		PEARL_STYLE_MAP.put(3, "Triple");
		PEARL_STYLE_MAP.put(4, "Other");
	}
	
	public static Map<Integer, String> DIAMOND_CUTLET_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_CUTLET_MAP.put(1, "Very Small");
		DIAMOND_CUTLET_MAP.put(2, "Small");
		DIAMOND_CUTLET_MAP.put(3, "Medium");
		DIAMOND_CUTLET_MAP.put(4, "Slightly Large");
		DIAMOND_CUTLET_MAP.put(5, "Large");
		DIAMOND_CUTLET_MAP.put(6, "Very Large");
		DIAMOND_CUTLET_MAP.put(7, "Extremely Large");
		DIAMOND_CUTLET_MAP.put(8, "NON");
	}
	
	public static Map<Integer, String> DIAMOND_FLUORESCENCE_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_FLUORESCENCE_MAP.put(1, "None");
		DIAMOND_FLUORESCENCE_MAP.put(2, "Faint");
		DIAMOND_FLUORESCENCE_MAP.put(3, "Medium");
		DIAMOND_FLUORESCENCE_MAP.put(4, "Strong");
		DIAMOND_FLUORESCENCE_MAP.put(5, "Very Strong");
	}
	
	public static Map<Integer, String> DIAMOND_GIRDLE_THICKNESS_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_GIRDLE_THICKNESS_MAP.put(1, "Rough");
		DIAMOND_GIRDLE_THICKNESS_MAP.put(2, "Polished");
		DIAMOND_GIRDLE_THICKNESS_MAP.put(3, "Faceted");
		DIAMOND_GIRDLE_THICKNESS_MAP.put(4, "Wavy");
		DIAMOND_GIRDLE_THICKNESS_MAP.put(5, "Bruted");
		DIAMOND_GIRDLE_THICKNESS_MAP.put(6, "Lasered");
		DIAMOND_GIRDLE_THICKNESS_MAP.put(7, "Not Applicable");
	}
	
	public static Map<Integer, String> DIAMOND_SECONDARY_HUE_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_SECONDARY_HUE_MAP.put(1, "Yellowish");
		DIAMOND_SECONDARY_HUE_MAP.put(2, "Blueish");
		DIAMOND_SECONDARY_HUE_MAP.put(3, "Greenish");
		DIAMOND_SECONDARY_HUE_MAP.put(4, "Orangeish");
		DIAMOND_SECONDARY_HUE_MAP.put(5, "Brownish");
		DIAMOND_SECONDARY_HUE_MAP.put(6, "Greyish");
		DIAMOND_SECONDARY_HUE_MAP.put(7, "Pinkish");
	}
	
	public static Map<Integer, String> DIAMOND_THICKNESS_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_THICKNESS_MAP.put(1, "ExtremelyThick");
		DIAMOND_THICKNESS_MAP.put(2, "VeryThick");
		DIAMOND_THICKNESS_MAP.put(3, "Thick");
		DIAMOND_THICKNESS_MAP.put(4, "SlightlyThick");
		DIAMOND_THICKNESS_MAP.put(5, "Medium");
		DIAMOND_THICKNESS_MAP.put(6, "SlightlyThin");
		DIAMOND_THICKNESS_MAP.put(7, "Thin");
		DIAMOND_THICKNESS_MAP.put(8, "VeryThin");
		DIAMOND_THICKNESS_MAP.put(9, "ExtremelyThin");
		DIAMOND_THICKNESS_MAP.put(10, "NON");
	}
	
	public static Map<Integer, String> METAL_TYPE_DETERMINED_MAP = new HashMap<Integer, String>();
	
	static {
		METAL_TYPE_DETERMINED_MAP.put(1, "Stamped");
		METAL_TYPE_DETERMINED_MAP.put(2, "Tested");
		METAL_TYPE_DETERMINED_MAP.put(3, "Other");
	}
	
	public static Map<Integer, String> PEARL_TYPE_MAP = new HashMap<Integer, String>();
	
	static {
		PEARL_TYPE_MAP.put(1, "Cultured");
		PEARL_TYPE_MAP.put(2, "Freshwater Cultured");
		PEARL_TYPE_MAP.put(3, "Mabe");
		PEARL_TYPE_MAP.put(4, "South Sea Cultured");
		PEARL_TYPE_MAP.put(5, "Natural");
		PEARL_TYPE_MAP.put(6, "Imitation");
		PEARL_TYPE_MAP.put(7, "Seed Pearl");
		PEARL_TYPE_MAP.put(8, "Salt Water Cultured");
		PEARL_TYPE_MAP.put(9, "Akoya");
	}
	
	public static Map<Integer, String> PEARL_COMPOSITION_MAP = new HashMap<Integer, String>();
	
	static {
		PEARL_COMPOSITION_MAP.put(1, "Uniform");
		PEARL_COMPOSITION_MAP.put(2, "Graduated");
	}
	
	public static Map<Integer, String> PEARL_BLEMISH_MAP = new HashMap<Integer, String>();
	
	static {
		PEARL_BLEMISH_MAP.put(1, "Clean");
		PEARL_BLEMISH_MAP.put(2, "Lightly Blemished");
		PEARL_BLEMISH_MAP.put(3, "Moderately Blemished");
		PEARL_BLEMISH_MAP.put(4, "Heavily Blemished");
	}
	
	public static Map<Integer, String> PEARL_ENHANCEMENTS_MAP = new HashMap<Integer, String>();
	
	static {
		PEARL_ENHANCEMENTS_MAP.put(1, "Dyeing");
		PEARL_ENHANCEMENTS_MAP.put(2, "Irradiation");
	}
	
	public static Map<Integer, String> COMPONENT_QUALITY_MAP = new HashMap<Integer, String>();
	
	static {
		COMPONENT_QUALITY_MAP.put(1, "Excellent");
		COMPONENT_QUALITY_MAP.put(2, "Very Good");
		COMPONENT_QUALITY_MAP.put(3, "Good");
		COMPONENT_QUALITY_MAP.put(4, "Fair");
		COMPONENT_QUALITY_MAP.put(5, "Poor");
	}
	
	public static Map<Integer, String> PEARL_DRILLED_MAP = new HashMap<Integer, String>();
	
	static {
		PEARL_DRILLED_MAP.put(1, "Side");
		PEARL_DRILLED_MAP.put(2, "Top");
	}
	
	
	public static Map<Integer, String> COMPONENT_PLACEMENT_MAP = new HashMap<Integer, String>();
	
	static {
		COMPONENT_PLACEMENT_MAP.put(1, "Mounted");
		COMPONENT_PLACEMENT_MAP.put(2, "Unmounted");
	}
	
	public static Map<Integer, String> COMPONENT_SHAPE_MAP = new HashMap<Integer, String>();
	
	static {
		COMPONENT_SHAPE_MAP.put(1, "Round");
		COMPONENT_SHAPE_MAP.put(2, "Princess");
		COMPONENT_SHAPE_MAP.put(3, "Emerald");
		COMPONENT_SHAPE_MAP.put(4, "Asscher");
		COMPONENT_SHAPE_MAP.put(5, "Cushion");
		COMPONENT_SHAPE_MAP.put(6, "Marquise");
		COMPONENT_SHAPE_MAP.put(7, "Radiant");
		COMPONENT_SHAPE_MAP.put(8, "Oval");
		COMPONENT_SHAPE_MAP.put(9, "Pear");
		COMPONENT_SHAPE_MAP.put(10, "Heart");
		COMPONENT_SHAPE_MAP.put(11, "Straight Baguette");
		COMPONENT_SHAPE_MAP.put(12, "Trillion");
		COMPONENT_SHAPE_MAP.put(13, "Round");
		COMPONENT_SHAPE_MAP.put(14, "Antique");
		COMPONENT_SHAPE_MAP.put(15, "Emerald");
		COMPONENT_SHAPE_MAP.put(16, "Heart");
		COMPONENT_SHAPE_MAP.put(17, "Square");
		COMPONENT_SHAPE_MAP.put(18, "Straight Baguette");
		COMPONENT_SHAPE_MAP.put(19, "Radiant");
		COMPONENT_SHAPE_MAP.put(20, "Trillion");
		COMPONENT_SHAPE_MAP.put(21, "Pear");
		COMPONENT_SHAPE_MAP.put(22, "Round");
		COMPONENT_SHAPE_MAP.put(23, "Oval");
		COMPONENT_SHAPE_MAP.put(24, "Button");
		COMPONENT_SHAPE_MAP.put(25, "Drop");
		COMPONENT_SHAPE_MAP.put(26, "Semi-Baroque");
		COMPONENT_SHAPE_MAP.put(27, "Baroque");
		COMPONENT_SHAPE_MAP.put(28, "Rice");
	}

	public static Map<Integer, String> GEMSTONE_ENHANCEMENT_MAP = new HashMap<Integer, String>();
	
	static {
		GEMSTONE_ENHANCEMENT_MAP.put(1, "Natural");
		GEMSTONE_ENHANCEMENT_MAP.put(2, "Irradiation");
		GEMSTONE_ENHANCEMENT_MAP.put(3, "Fracture Filled");
		GEMSTONE_ENHANCEMENT_MAP.put(4, "Surface Dyes");
		GEMSTONE_ENHANCEMENT_MAP.put(5, "Beryllium");
		GEMSTONE_ENHANCEMENT_MAP.put(6, "Heat Treatment");
	}
	
	public static Map<Integer, String> GEMSTONE_CUT_MAP = new HashMap<Integer, String>();
	
	static {
		GEMSTONE_CUT_MAP.put(1, "Faceted");
		GEMSTONE_CUT_MAP.put(2, "Cabochon");
		GEMSTONE_CUT_MAP.put(3, "Cameo");
		GEMSTONE_CUT_MAP.put(4, "Intaglio");
		GEMSTONE_CUT_MAP.put(5, "Chevee");
		GEMSTONE_CUT_MAP.put(6, "Cuvette");
		GEMSTONE_CUT_MAP.put(7, "Bead");
	}
	
	public static Map<Integer, String> GEMSTONE_TYPE_MAP = new HashMap<Integer, String>();
	
	static {
		GEMSTONE_TYPE_MAP.put(1, "Alexandrite");
		GEMSTONE_TYPE_MAP.put(2, "Andalusite");
		GEMSTONE_TYPE_MAP.put(3, "Axinite");
		GEMSTONE_TYPE_MAP.put(4, "Benitoite");
		GEMSTONE_TYPE_MAP.put(5, "Aquamarine");
		GEMSTONE_TYPE_MAP.put(6, "Bixbite");
		GEMSTONE_TYPE_MAP.put(7, "Emerald");
		GEMSTONE_TYPE_MAP.put(8, "Morganite");
		GEMSTONE_TYPE_MAP.put(9, "Bloodstone");
		GEMSTONE_TYPE_MAP.put(10, "Cassiterite");
		GEMSTONE_TYPE_MAP.put(11, "Celestite");
		GEMSTONE_TYPE_MAP.put(12, "Cat's Eye");
		GEMSTONE_TYPE_MAP.put(13, "Chrysocolla");
		GEMSTONE_TYPE_MAP.put(14, "Chrysoprase");
		GEMSTONE_TYPE_MAP.put(15, "Clinohumite");
		GEMSTONE_TYPE_MAP.put(16, "Cordierite");
		GEMSTONE_TYPE_MAP.put(17, "Ruby");
		GEMSTONE_TYPE_MAP.put(18, "Sapphire");
		GEMSTONE_TYPE_MAP.put(19, "Danburite");
		GEMSTONE_TYPE_MAP.put(20, "Diamond");
		GEMSTONE_TYPE_MAP.put(21, "Diopside");
		GEMSTONE_TYPE_MAP.put(22, "Dioptase");
		GEMSTONE_TYPE_MAP.put(23, "Dumortierite");
		GEMSTONE_TYPE_MAP.put(24, "Amazonite");
		GEMSTONE_TYPE_MAP.put(25, "Labradorite");
		GEMSTONE_TYPE_MAP.put(26, "Moonstone");
		GEMSTONE_TYPE_MAP.put(27, "Sunstone");
		GEMSTONE_TYPE_MAP.put(28, "Fluorite");
		GEMSTONE_TYPE_MAP.put(29, "Hessonite");
		GEMSTONE_TYPE_MAP.put(30, "Hambergite");
		GEMSTONE_TYPE_MAP.put(31, "Hematite");
		GEMSTONE_TYPE_MAP.put(32, "Jadeite");
		GEMSTONE_TYPE_MAP.put(33, "Nephrite");
		GEMSTONE_TYPE_MAP.put(34, "Jasper");
		GEMSTONE_TYPE_MAP.put(35, "Kornerupine");
		GEMSTONE_TYPE_MAP.put(36, "Kunzite");
		GEMSTONE_TYPE_MAP.put(37, "Malachite");
		GEMSTONE_TYPE_MAP.put(38, "Peridot");
		GEMSTONE_TYPE_MAP.put(39, "Prehnite");
		GEMSTONE_TYPE_MAP.put(40, "Pyrite");
		GEMSTONE_TYPE_MAP.put(41, "Amethyst");
		GEMSTONE_TYPE_MAP.put(42, "Citrine");
		GEMSTONE_TYPE_MAP.put(43, "Smoky Quartz");
		GEMSTONE_TYPE_MAP.put(44, "Tiger's-eye");
		GEMSTONE_TYPE_MAP.put(45, "Agate (Chalcedony)");
		GEMSTONE_TYPE_MAP.put(46, "Aventurine (Chalcedony)");
		GEMSTONE_TYPE_MAP.put(47, "Onyx (Chalcedony)");
		GEMSTONE_TYPE_MAP.put(48, "Rhodochrosite");
		GEMSTONE_TYPE_MAP.put(49, "Sérandite");
		GEMSTONE_TYPE_MAP.put(50, "Spinel");
		GEMSTONE_TYPE_MAP.put(51, "Sugilite");
		GEMSTONE_TYPE_MAP.put(52, "Topaz");
		GEMSTONE_TYPE_MAP.put(53, "Turquoise");
		GEMSTONE_TYPE_MAP.put(54, "Tourmaline");
		GEMSTONE_TYPE_MAP.put(55, "Variscite");
		GEMSTONE_TYPE_MAP.put(56, "Vesuvianite");
		GEMSTONE_TYPE_MAP.put(57, "Xenotime");
		GEMSTONE_TYPE_MAP.put(58, "Zeolite (Thomsonite)");
		GEMSTONE_TYPE_MAP.put(59, "Zircon");
		GEMSTONE_TYPE_MAP.put(60, "Tanzanite");
		GEMSTONE_TYPE_MAP.put(61, "Thulite");
	}
	
	public static Map<Integer, String> EXTERNAL_INCLUSION_MAP = new HashMap<Integer, String>();
	
	static {
		EXTERNAL_INCLUSION_MAP.put(1, "Pit");
		EXTERNAL_INCLUSION_MAP.put(2, "Cavity");
		EXTERNAL_INCLUSION_MAP.put(3, "Chip");
		EXTERNAL_INCLUSION_MAP.put(4, "Nick");
		EXTERNAL_INCLUSION_MAP.put(5, "Surface Grain Line");
		EXTERNAL_INCLUSION_MAP.put(6, "Scratch");
		EXTERNAL_INCLUSION_MAP.put(7, "Extra Facet");
		EXTERNAL_INCLUSION_MAP.put(8, "Other");
	}
	
	public static Map<Integer, String> INTERNAL_INCLUSION_MAP = new HashMap<Integer, String>();
	
	static {
		INTERNAL_INCLUSION_MAP.put(1, "Feather");
		INTERNAL_INCLUSION_MAP.put(2, "Crystal");
		INTERNAL_INCLUSION_MAP.put(3, "Dark Crystal");
		INTERNAL_INCLUSION_MAP.put(4, "Pinpoint Single");
		INTERNAL_INCLUSION_MAP.put(5, "Pinpoint Cluster");
		INTERNAL_INCLUSION_MAP.put(6, "Cloud");
		INTERNAL_INCLUSION_MAP.put(7, "Knot");
		INTERNAL_INCLUSION_MAP.put(8, "Bruise");
		INTERNAL_INCLUSION_MAP.put(9, "Internal Grain Line");
		INTERNAL_INCLUSION_MAP.put(10, "Feathered Girdle");
		INTERNAL_INCLUSION_MAP.put(11, "Laser Drill Hole");
		INTERNAL_INCLUSION_MAP.put(12, "Cavity");
		INTERNAL_INCLUSION_MAP.put(13, "Wisp");
		INTERNAL_INCLUSION_MAP.put(14, "Indented Natural");
		INTERNAL_INCLUSION_MAP.put(15, "Needle-like");
	}
	
	public static Map<Integer, String> METAL_PURITY_MAP = new HashMap<Integer, String>();
	
	static {
		METAL_PURITY_MAP.put(1, "24k");
		METAL_PURITY_MAP.put(2, "22k");
		METAL_PURITY_MAP.put(3, "22k Coins");
		METAL_PURITY_MAP.put(4, "900 Coins");
		METAL_PURITY_MAP.put(5, "21k");
		METAL_PURITY_MAP.put(6, "18k");
		METAL_PURITY_MAP.put(7, "16k");
		METAL_PURITY_MAP.put(8, "14k");
		METAL_PURITY_MAP.put(9, "12k");
		METAL_PURITY_MAP.put(10, "10k");
		METAL_PURITY_MAP.put(11, "9k");
		METAL_PURITY_MAP.put(12, "Pure");
		METAL_PURITY_MAP.put(13, "Sterling");
		METAL_PURITY_MAP.put(14, "800 Silver");
		METAL_PURITY_MAP.put(15, "600 Silver");
		METAL_PURITY_MAP.put(16, "400 Silver");
		METAL_PURITY_MAP.put(17, "Pure");
		METAL_PURITY_MAP.put(18, "90%");
	}
	
	public static Map<Integer, String> DIAMOND_COLOR_MAP = new HashMap<Integer, String>();
	
	static {
		DIAMOND_COLOR_MAP.put(1, "D");
		DIAMOND_COLOR_MAP.put(2, "E");
		DIAMOND_COLOR_MAP.put(3, "F");
		DIAMOND_COLOR_MAP.put(4, "G");
		DIAMOND_COLOR_MAP.put(5, "H");
		DIAMOND_COLOR_MAP.put(6, "I");
		DIAMOND_COLOR_MAP.put(7, "J");
		DIAMOND_COLOR_MAP.put(8, "K");
		DIAMOND_COLOR_MAP.put(9, "L");
		DIAMOND_COLOR_MAP.put(10, "M");
		DIAMOND_COLOR_MAP.put(11, "N");
		DIAMOND_COLOR_MAP.put(12, "O");
		DIAMOND_COLOR_MAP.put(13, "P");
		DIAMOND_COLOR_MAP.put(14, "Q");
		DIAMOND_COLOR_MAP.put(15, "R");
		DIAMOND_COLOR_MAP.put(16, "S");
		DIAMOND_COLOR_MAP.put(17, "T");
		DIAMOND_COLOR_MAP.put(18, "U");
		DIAMOND_COLOR_MAP.put(19, "V");
		DIAMOND_COLOR_MAP.put(20, "W");
		DIAMOND_COLOR_MAP.put(21, "X");
		DIAMOND_COLOR_MAP.put(22, "Y");
		DIAMOND_COLOR_MAP.put(23, "Z");
	}
	
	public static Map<Integer, String> FANCY_COLOR_MAP = new HashMap<Integer, String>();
	
	static {
		FANCY_COLOR_MAP.put(1, "Yellow");
		FANCY_COLOR_MAP.put(2, "Blue");
		FANCY_COLOR_MAP.put(3, "Green");
		FANCY_COLOR_MAP.put(4, "Orange");
		FANCY_COLOR_MAP.put(5, "Black");   
		FANCY_COLOR_MAP.put(6, "Brown");   
		FANCY_COLOR_MAP.put(7, "Grey");
		FANCY_COLOR_MAP.put(8, "Pink");
		FANCY_COLOR_MAP.put(9, "White");
		FANCY_COLOR_MAP.put(10, "Red");
	}
	
	public static Map<Integer, String> PEARL_COLOR_MAP = new HashMap<Integer, String>();
	
	static {
		PEARL_COLOR_MAP.put(1, "White-Rose");
		PEARL_COLOR_MAP.put(2, "White-Silver");
		PEARL_COLOR_MAP.put(3, "White-Cream");
		PEARL_COLOR_MAP.put(4, "White-Ivory");
		PEARL_COLOR_MAP.put(5, "White-Purplish");
		PEARL_COLOR_MAP.put(6, "White-Pink");
		PEARL_COLOR_MAP.put(7, "White-Peach");
		PEARL_COLOR_MAP.put(8, "White-Lavender");
		PEARL_COLOR_MAP.put(9, "Black-PaleSilver");
		PEARL_COLOR_MAP.put(10, "Black-DoveGrey");
		PEARL_COLOR_MAP.put(11, "Black-VeryDarkCharcoalGrey");
		PEARL_COLOR_MAP.put(12, "Black-Peacock(mixofGreenGoldPinkhues)");
		PEARL_COLOR_MAP.put(13, "Black-Silver/Steel");
		PEARL_COLOR_MAP.put(14, "Black-Blue-Green");
		PEARL_COLOR_MAP.put(15, "Black-EggplantPurple");
		PEARL_COLOR_MAP.put(16, "Black-Cherry");
		PEARL_COLOR_MAP.put(17, "Black-SkyBlue");
		PEARL_COLOR_MAP.put(18, "Black-Pistachio");
		PEARL_COLOR_MAP.put(19, "Black-Chocolate");
		PEARL_COLOR_MAP.put(20, "Black-MultiColor");
		PEARL_COLOR_MAP.put(21, "Golden-NeutralGolden");
		PEARL_COLOR_MAP.put(22, "Golden-Champagne");
		PEARL_COLOR_MAP.put(23, "Golden-Green/Bronze");
		PEARL_COLOR_MAP.put(24, "Golden-Rose");
		PEARL_COLOR_MAP.put(25, "Golden-Pink");
		PEARL_COLOR_MAP.put(26, "Golden-Peach");
	}
	
	public static Map<Integer, String[]> DIAMOND_SHAPE_MAP = new HashMap<Integer, String[]>();
	
	static {
		DIAMOND_SHAPE_MAP.put(1, new String[]{"Briolette (13)", "other"});
		DIAMOND_SHAPE_MAP.put(2, new String[]{"Bat Mixed Cut (R68)", "other"});
		DIAMOND_SHAPE_MAP.put(3, new String[]{"Bent Top Cut-Cornered Rectangular Step Cut (E67)", "other"});
		DIAMOND_SHAPE_MAP.put(4, new String[]{"Bird Modified Brilliant (R89)", "other"});
		DIAMOND_SHAPE_MAP.put(5, new String[]{"Boat Novelty Cut (R11)", "other"});
		DIAMOND_SHAPE_MAP.put(6, new String[]{"Blocked Round", "other"});
		DIAMOND_SHAPE_MAP.put(7, new String[]{"Bent Top Rectangular Step Cut (E01)", "other"});
		DIAMOND_SHAPE_MAP.put(8, new String[]{"Buff Top Round Mixed Cut (A70)", "other"});
		DIAMOND_SHAPE_MAP.put(9, new String[]{"Bent Top Square Step Cut (G46)", "other"});
		DIAMOND_SHAPE_MAP.put(10, new String[]{"Bull Step Cut (OLD)", "other"});
		DIAMOND_SHAPE_MAP.put(11, new String[]{"Butterfly Modified Brilliant (R01)", "other"});
		DIAMOND_SHAPE_MAP.put(12, new String[]{"Cushion Brilliant (4M - F04)", "other"});
		DIAMOND_SHAPE_MAP.put(13, new String[]{"Cut-Cornered Rectangular Rose Cut (E86)", "other"});
		DIAMOND_SHAPE_MAP.put(14, new String[]{"Circular Brilliant (A113)", "round"});
		DIAMOND_SHAPE_MAP.put(15, new String[]{"Clover Modified Brilliant (R69)", "other"});
		DIAMOND_SHAPE_MAP.put(16, new String[]{"Cushion Modified Brilliant", "other"});
		DIAMOND_SHAPE_MAP.put(17, new String[]{"Cushion Novelty Cut (F29)", "other"});
		DIAMOND_SHAPE_MAP.put(18, new String[]{"Cushion Portrait Cut (F19)", "other"});
		DIAMOND_SHAPE_MAP.put(19, new String[]{"Cushion Rose Cut (F21)", "other"});
		DIAMOND_SHAPE_MAP.put(20, new String[]{"Cut-Cornered Rectangular Modified Brilliant (E07)", "other"});
		DIAMOND_SHAPE_MAP.put(21, new String[]{"Cut-Cornered Rectangular Novelty Cut (E15)", "other"});
		DIAMOND_SHAPE_MAP.put(22, new String[]{"Modified Cross Brilliant (R13)", "other"});
		DIAMOND_SHAPE_MAP.put(23, new String[]{"Cut-Cornered Rectangular Portrait Cut (E22)", "other"});
		DIAMOND_SHAPE_MAP.put(24, new String[]{"Cut-Cornered Rectangular Rose Cut (E69)", "other"});
		DIAMOND_SHAPE_MAP.put(25, new String[]{"Cut-Cornered Rectangular Step Cut (E42)", "other"});
		DIAMOND_SHAPE_MAP.put(26, new String[]{"Cross Modified Brilliant (OLD)", "other"});
		DIAMOND_SHAPE_MAP.put(27, new String[]{"Cut-Cornered Rectangular Mixed Cut (E16)", "other"});
		DIAMOND_SHAPE_MAP.put(28, new String[]{"Cushion Step Cut (F13)", "other"});
		DIAMOND_SHAPE_MAP.put(29, new String[]{"Cut-Cornered Square Modified Brilliant (G105)", "other"});
		DIAMOND_SHAPE_MAP.put(30, new String[]{"Cut-Cornered Square Portrait Cut (G55)", "other"});
		DIAMOND_SHAPE_MAP.put(31, new String[]{"Cut-Cornered Square Step Cut (G06)", "other"});
		DIAMOND_SHAPE_MAP.put(32, new String[]{"Cut-Cornered Square Mixed Cut (G100)", "other"});
		DIAMOND_SHAPE_MAP.put(33, new String[]{"Cut-Cornered Trapezoid Modified Brilliant (T06)", "other"});
		DIAMOND_SHAPE_MAP.put(34, new String[]{"Cut-Cornered Trapezoid Step Cut (T02)", "other"});
		DIAMOND_SHAPE_MAP.put(35, new String[]{"Cut-Cornered Trapezoid Mixed Cut (T38)", "other"});
		DIAMOND_SHAPE_MAP.put(36, new String[]{"Cut-Cornered Triangular Modified Brilliant (I13)", "other"});
		DIAMOND_SHAPE_MAP.put(37, new String[]{"Cut-Cornered Triangular Step Cut (I16)", "other"});
		DIAMOND_SHAPE_MAP.put(38, new String[]{"Cut-Cornered Triangular Mixed Cut (I14)", "other"});
		DIAMOND_SHAPE_MAP.put(39, new String[]{"Cushion Mixed Cut (F10)", "other"});
		DIAMOND_SHAPE_MAP.put(40, new String[]{"Decagonal Brilliant (L15)", "round"});
		DIAMOND_SHAPE_MAP.put(41, new String[]{"Dolphin Step Cut (R20)", "other"});
		DIAMOND_SHAPE_MAP.put(42, new String[]{"Dolphin Double Rose Cut (R59)", "other"});
		DIAMOND_SHAPE_MAP.put(43, new String[]{"Decagonal Modified Brilliant (L13)", "round"});
		DIAMOND_SHAPE_MAP.put(44, new String[]{"Eagle Brilliant (R26)", "other"});
		DIAMOND_SHAPE_MAP.put(45, new String[]{"Elephant Step Cut (R28)", "other"});
		DIAMOND_SHAPE_MAP.put(46, new String[]{"Emerald Cut (3 Step, E21)", "other"});
		DIAMOND_SHAPE_MAP.put(47, new String[]{"Faceted Bead (U01)", "other"});
		DIAMOND_SHAPE_MAP.put(48, new String[]{"Fish Modified Brilliant (R08)", "other"});
		DIAMOND_SHAPE_MAP.put(49, new String[]{"Flame Step Cut (R16)", "other"});
		DIAMOND_SHAPE_MAP.put(50, new String[]{"Flame Modified Brilliant (R72)", "other"});
		DIAMOND_SHAPE_MAP.put(51, new String[]{"Flower Brilliant (R38)", "other"});
		DIAMOND_SHAPE_MAP.put(52, new String[]{"Gun Step Cut (R87)", "other"});
		DIAMOND_SHAPE_MAP.put(53, new String[]{"Heart Brilliant (3M/B - H01)", "other"});
		DIAMOND_SHAPE_MAP.put(54, new String[]{"Heart Double Rose Cut (H34)", "other"});
		DIAMOND_SHAPE_MAP.put(55, new String[]{"Heart Modified Brilliant (H53)", "other"});
		DIAMOND_SHAPE_MAP.put(56, new String[]{"Half-Moon Mixed Cut (P02)", "other"});
		DIAMOND_SHAPE_MAP.put(57, new String[]{"Heart Novelty Cut (H32)", "other"});
		DIAMOND_SHAPE_MAP.put(58, new String[]{"Heart Portrait Cut (H31)", "other"});
		DIAMOND_SHAPE_MAP.put(59, new String[]{"Heart Rose Cut (H35)", "other"});
		DIAMOND_SHAPE_MAP.put(60, new String[]{"Horse Modified Brilliant (R10)", "other"});
		DIAMOND_SHAPE_MAP.put(61, new String[]{"Heart Step Cut (H29)", "other"});
		DIAMOND_SHAPE_MAP.put(62, new String[]{"Heart Mixed Cut (H58)", "other"});
		DIAMOND_SHAPE_MAP.put(63, new String[]{"Hexagonal Brilliant (K01)", "round"});
		DIAMOND_SHAPE_MAP.put(64, new String[]{"Hexoctahedral Cut", "round"});
		DIAMOND_SHAPE_MAP.put(65, new String[]{"Hexagonal Modified Brilliant (K02)", "round"});
		DIAMOND_SHAPE_MAP.put(66, new String[]{"Hexagonal Step Cut (K05)", "round"});
		DIAMOND_SHAPE_MAP.put(67, new String[]{"Hexagonal Mixed Cut (K12)", "round"});
		DIAMOND_SHAPE_MAP.put(68, new String[]{"Kite Brilliant (N01)", "other"});
		DIAMOND_SHAPE_MAP.put(69, new String[]{"Kite Double Rose Cut (N20)", "other"});
		DIAMOND_SHAPE_MAP.put(70, new String[]{"Kite Step Cut (N04)", "other"});
		DIAMOND_SHAPE_MAP.put(71, new String[]{"Kite Mixed Cut (N02)", "other"});
		DIAMOND_SHAPE_MAP.put(72, new String[]{"Lozenge Brilliant (O01)", "other"});
		DIAMOND_SHAPE_MAP.put(73, new String[]{"Leaf Mixed Cut (R46)", "other"});
		DIAMOND_SHAPE_MAP.put(74, new String[]{"Lozenge Modified Brilliant (O02)", "other"});
		DIAMOND_SHAPE_MAP.put(75, new String[]{"Lozenge Portrait Cut (O33)", "other"});
		DIAMOND_SHAPE_MAP.put(76, new String[]{"Lozenge Step Cut (O05)", "other"});
		DIAMOND_SHAPE_MAP.put(77, new String[]{"Lozenge Mixed Cut (O03)", "other"});
		DIAMOND_SHAPE_MAP.put(78, new String[]{"Marquise Brilliant (4M/B - B01)", "other"});
		DIAMOND_SHAPE_MAP.put(79, new String[]{"Marquise Double Rose Cut (B31)", "other"});
		DIAMOND_SHAPE_MAP.put(80, new String[]{"Modified Heart Brilliant (H14)", "other"});
		DIAMOND_SHAPE_MAP.put(81, new String[]{"Modified Heart Double Rose Cut (H30)", "other"});
		DIAMOND_SHAPE_MAP.put(82, new String[]{"Modified Half-Moon Brilliant Cut (P04)", "other"});
		DIAMOND_SHAPE_MAP.put(83, new String[]{"Modified Heart Rose Cut (H24)", "other"});
		DIAMOND_SHAPE_MAP.put(84, new String[]{"Modified Heart Step Cut (H17)", "other"});
		DIAMOND_SHAPE_MAP.put(85, new String[]{"Modified Heart Mixed Cut (H21)", "other"});
		DIAMOND_SHAPE_MAP.put(86, new String[]{"Modified Hexagonal Brilliant (K06)", "round"});
		DIAMOND_SHAPE_MAP.put(87, new String[]{"Modified Hexagonal Portrait Cut (K17)", "round"});
		DIAMOND_SHAPE_MAP.put(88, new String[]{"Modified Hexagonal Rose Cut (K18)", "round"});
		DIAMOND_SHAPE_MAP.put(89, new String[]{"Modified Hexagonal Step Cut (K10)", "round"});
		DIAMOND_SHAPE_MAP.put(90, new String[]{"Modified Hexagonal Mixed Cut (K08)", "round"});
		DIAMOND_SHAPE_MAP.put(91, new String[]{"Modified Kite Brilliant (N08)", "other"});
		DIAMOND_SHAPE_MAP.put(92, new String[]{"Modified Kite Portrait Cut (N18)", "other"});
		DIAMOND_SHAPE_MAP.put(93, new String[]{"Modified Kite Step Cut (N10)", "other"});
		DIAMOND_SHAPE_MAP.put(94, new String[]{"Modified Kite Mixed Cut (N09)", "other"});
		DIAMOND_SHAPE_MAP.put(95, new String[]{"Modified Lozenge Brilliant (O06)", "other"});
		DIAMOND_SHAPE_MAP.put(96, new String[]{"Maple Leaf Modified Brilliant (R86)", "other"});
		DIAMOND_SHAPE_MAP.put(97, new String[]{"Modified Lozenge Step Cut (O30)", "other"});
		DIAMOND_SHAPE_MAP.put(98, new String[]{"Modified Lozenge Mixed Cut (O08)", "other"});
		DIAMOND_SHAPE_MAP.put(99, new String[]{"Marquise Modified Brilliant (14M/B - B22)", "other"});
		DIAMOND_SHAPE_MAP.put(100, new String[]{"Marquise Mixed Cut (B27)", "other"});
		DIAMOND_SHAPE_MAP.put(101, new String[]{"Modified Marquise Double Rose Cut (B25)", "other"});
		DIAMOND_SHAPE_MAP.put(102, new String[]{"Modified Marquise Step Cut (B13)", "other"});
		DIAMOND_SHAPE_MAP.put(103, new String[]{"Modified Marquise Mixed Cut (B12)", "other"});
		DIAMOND_SHAPE_MAP.put(104, new String[]{"Marquise Novelty Cut (B26)", "other"});
		DIAMOND_SHAPE_MAP.put(105, new String[]{"Modified Oval Brilliant (D45)", "other"});
		DIAMOND_SHAPE_MAP.put(106, new String[]{"Modified Octahedron Cut (G109)", "round"});
		DIAMOND_SHAPE_MAP.put(107, new String[]{"Modified Octagonal Brilliant (L12)", "round"});
		DIAMOND_SHAPE_MAP.put(108, new String[]{"Modified Octagonal Step Cut (L14)", "round"});
		DIAMOND_SHAPE_MAP.put(109, new String[]{"Modified Octagonal Step Cut (L21)", "round"});
		DIAMOND_SHAPE_MAP.put(110, new String[]{"Modified Oval Step Cut (D48)", "other"});
		DIAMOND_SHAPE_MAP.put(111, new String[]{"Mouse Character Rose Cut (R18)", "other"});
		DIAMOND_SHAPE_MAP.put(112, new String[]{"Modified Oval Mixed Cut (D47)", "other"});
		DIAMOND_SHAPE_MAP.put(113, new String[]{"Modified Pear Brilliant (C18)", "other"});
		DIAMOND_SHAPE_MAP.put(114, new String[]{"Marquise Portrait Cut (B24)", "other"});
		DIAMOND_SHAPE_MAP.put(115, new String[]{"Modified Pear Double Rose Cut (C44)", "other"});
		DIAMOND_SHAPE_MAP.put(116, new String[]{"Modified Pear Portrait Cut (C47)", "other"});
		DIAMOND_SHAPE_MAP.put(117, new String[]{"Modified Pear Rose Cut (C48)", "other"});
		DIAMOND_SHAPE_MAP.put(118, new String[]{"Modified Pear Step Cut (C21)", "other"});
		DIAMOND_SHAPE_MAP.put(119, new String[]{"Modified Pentagonal Brilliant (J05)", "other"});
		DIAMOND_SHAPE_MAP.put(120, new String[]{"Modified Pentagonal Step Cut (J06)", "other"});
		DIAMOND_SHAPE_MAP.put(121, new String[]{"Modified Pentagonal Mixed Cut (J09)", "other"});
		DIAMOND_SHAPE_MAP.put(122, new String[]{"Modified Pear Mixed Cut (C20)", "other"});
		DIAMOND_SHAPE_MAP.put(123, new String[]{"Modified Rectangular Brilliant (E28)", "other"});
		DIAMOND_SHAPE_MAP.put(124, new String[]{"Marquise Rose Cut (B20)", "other"});
		DIAMOND_SHAPE_MAP.put(125, new String[]{"Modified Rhomboid Brilliant (J43)", "other"});
		DIAMOND_SHAPE_MAP.put(126, new String[]{"Modified Rectangular Mixed Cut (E131)", "other"});
		DIAMOND_SHAPE_MAP.put(127, new String[]{"Modified Rectangular Novelty Cut (E88)", "other"});
		DIAMOND_SHAPE_MAP.put(128, new String[]{"Modified Rectangular Step Cut (E38)", "other"});
		DIAMOND_SHAPE_MAP.put(129, new String[]{"Modified Square Brilliant (G110)", "other"});
		DIAMOND_SHAPE_MAP.put(130, new String[]{"Marquise Step Cut (B21)", "other"});
		DIAMOND_SHAPE_MAP.put(131, new String[]{"Modified Shield Brilliant (M04)", "other"});
		DIAMOND_SHAPE_MAP.put(132, new String[]{"Modified Shield Novelty Cut (M52)", "other"});
		DIAMOND_SHAPE_MAP.put(133, new String[]{"Modified Shield Portrait Cut (M28)", "other"});
		DIAMOND_SHAPE_MAP.put(134, new String[]{"Modified Shield Rose Cut (M25)", "other"});
		DIAMOND_SHAPE_MAP.put(135, new String[]{"Modified Shield Step Cut (M07)", "other"});
		DIAMOND_SHAPE_MAP.put(136, new String[]{"Modified Shield Mixed Cut (M06)", "other"});
		DIAMOND_SHAPE_MAP.put(137, new String[]{"Modified Shield Novelty Cut (M26)", "other"});
		DIAMOND_SHAPE_MAP.put(138, new String[]{"Modified Shield Portrait Cut (M41)", "other"});
		DIAMOND_SHAPE_MAP.put(139, new String[]{"Modified Square Step Cut (G52)", "other"});
		DIAMOND_SHAPE_MAP.put(140, new String[]{"Modified Seven-Sided Novelty", "other"});
		DIAMOND_SHAPE_MAP.put(141, new String[]{"Modified Triangular Novelty Cut (I25)", "other"});
		DIAMOND_SHAPE_MAP.put(142, new String[]{"Modified Trapezoid Brilliant (T09)", "other"});
		DIAMOND_SHAPE_MAP.put(143, new String[]{"Modified Trapezoid Mixed Cut (T04)", "other"});
		DIAMOND_SHAPE_MAP.put(144, new String[]{"Modified Triangular Brilliant (I03)", "other"});
		DIAMOND_SHAPE_MAP.put(145, new String[]{"Modified Triangular Double Rose Cut (I31)", "other"});
		DIAMOND_SHAPE_MAP.put(146, new String[]{"Modified Triangular Rose Cut (I28)", "other"});
		DIAMOND_SHAPE_MAP.put(147, new String[]{"Modified Triangular Step Cut (I12)", "other"});
		DIAMOND_SHAPE_MAP.put(148, new String[]{"Modified Triangular Mixed Cut (I10)", "other"});
		DIAMOND_SHAPE_MAP.put(149, new String[]{"Nine-Sided Modified Brilliant (L25)", "round"});
		DIAMOND_SHAPE_MAP.put(150, new String[]{"Oval Brilliant (4M - D01)", "other"});
		DIAMOND_SHAPE_MAP.put(151, new String[]{"Octagonal Brilliant (L01)", "round"});
		DIAMOND_SHAPE_MAP.put(152, new String[]{"Octagonal Modified Brilliant (L03)", "round"});
		DIAMOND_SHAPE_MAP.put(153, new String[]{"Octagonal Portrait Cut (L07)", "round"});
		DIAMOND_SHAPE_MAP.put(154, new String[]{"Octagonal Step Cut (L06)", "round"});
		DIAMOND_SHAPE_MAP.put(155, new String[]{"Octagonal Mixed Cut (L04)", "round"});
		DIAMOND_SHAPE_MAP.put(156, new String[]{"Oval Double Rose Cut (D56)", "other"});
		DIAMOND_SHAPE_MAP.put(157, new String[]{"Old European Brilliant (A09)", "other"});
		DIAMOND_SHAPE_MAP.put(158, new String[]{"Old Mine Brilliant (F01)", "other"});
		DIAMOND_SHAPE_MAP.put(159, new String[]{"Oval Modified Portrait Cut (D52)", "other"});
		DIAMOND_SHAPE_MAP.put(160, new String[]{"Oval Novelty Cut (D58)", "other"});
		DIAMOND_SHAPE_MAP.put(161, new String[]{"Oval Portrait Cut (D50)", "other"});
		DIAMOND_SHAPE_MAP.put(162, new String[]{"Oval Rose Cut (D44)", "other"});
		DIAMOND_SHAPE_MAP.put(163, new String[]{"Oval Step Cut (D41)", "other"});
		DIAMOND_SHAPE_MAP.put(164, new String[]{"Arrow Novelty Cut (R91)", "other"});
		DIAMOND_SHAPE_MAP.put(165, new String[]{"Oval Mixed Cut (D42)", "other"});
		DIAMOND_SHAPE_MAP.put(166, new String[]{"Pear Brilliant (4M/B - C01)", "other"});
		DIAMOND_SHAPE_MAP.put(167, new String[]{"Pear Double Rose Cut (C27)", "other"});
		DIAMOND_SHAPE_MAP.put(168, new String[]{"Pear Modified Brilliant (C14)", "other"});
		DIAMOND_SHAPE_MAP.put(169, new String[]{"Pear Novelty Cut (C54)", "other"});
		DIAMOND_SHAPE_MAP.put(170, new String[]{"Pear Portrait Cut (C32)", "other"});
		DIAMOND_SHAPE_MAP.put(171, new String[]{"Pear Rose Cut (C17)", "other"});
		DIAMOND_SHAPE_MAP.put(172, new String[]{"Pear Step Cut (C31)", "other"});
		DIAMOND_SHAPE_MAP.put(173, new String[]{"Pentagonal Modified Brilliant (J02)", "other"});
		DIAMOND_SHAPE_MAP.put(174, new String[]{"Pentagonal Step Cut (J04)", "other"});
		DIAMOND_SHAPE_MAP.put(175, new String[]{"Pentagonal Mixed Cut (J03)", "other"});
		DIAMOND_SHAPE_MAP.put(176, new String[]{"Pear Mixed Cut (C30)", "other"});
		DIAMOND_SHAPE_MAP.put(177, new String[]{"Round Modified Brilliant (A116)", "round"});
		DIAMOND_SHAPE_MAP.put(178, new String[]{"Round Brilliant (A01)", "round"});
		DIAMOND_SHAPE_MAP.put(179, new String[]{"Racket Modified Brilliant (R04)", "other"});
		DIAMOND_SHAPE_MAP.put(180, new String[]{"Round-Cornered Rectangular Brilliant (E23)", "other"});
		DIAMOND_SHAPE_MAP.put(181, new String[]{"Round-Cornered Rhomboid Mixed Cut (J08)", "other"});
		DIAMOND_SHAPE_MAP.put(182, new String[]{"Round-Cornered Rectangular Modified Brilliant (E128)", "other"});
		DIAMOND_SHAPE_MAP.put(183, new String[]{"Round-Cornered Rectangular Portrait Cut (E100)", "other"});
		DIAMOND_SHAPE_MAP.put(184, new String[]{"Round-Cornered Rectangular Step Cut (E26)", "other"});
		DIAMOND_SHAPE_MAP.put(185, new String[]{"Round-Cornered Rectangular Mixed Cut (E25)", "other"});
		DIAMOND_SHAPE_MAP.put(186, new String[]{"Round-Cornered Square Brilliant (G97)", "other"});
		DIAMOND_SHAPE_MAP.put(187, new String[]{"Round-Cornered Square Modified Brilliant (G128)", "other"});
		DIAMOND_SHAPE_MAP.put(188, new String[]{"Round-Cornered Square Step Cut (G23)", "other"});
		DIAMOND_SHAPE_MAP.put(189, new String[]{"Round-Cornered Square Mixed Cut (G21)", "other"});
		DIAMOND_SHAPE_MAP.put(190, new String[]{"Rectangular Novelty Cut (E132)", "other"});
		DIAMOND_SHAPE_MAP.put(191, new String[]{"Rhomboid Step Cut (J01)", "other"});
		DIAMOND_SHAPE_MAP.put(192, new String[]{"Modified Round Brilliant (A75)", "round"});
		DIAMOND_SHAPE_MAP.put(193, new String[]{"Round Novelty Cut (A104)", "round"});
		DIAMOND_SHAPE_MAP.put(194, new String[]{"Rectangular Portrait Cut (E72)", "other"});
		DIAMOND_SHAPE_MAP.put(195, new String[]{"Round Rose Cut (A106)", "other"});
		DIAMOND_SHAPE_MAP.put(196, new String[]{"Rectangular Step Cut (E02)", "other"});
		DIAMOND_SHAPE_MAP.put(197, new String[]{"Round Cornered Triangular Modified Brilliant (I17)", "other"});
		DIAMOND_SHAPE_MAP.put(198, new String[]{"Rectangular Mixed Cut (E03)", "other"});
		DIAMOND_SHAPE_MAP.put(199, new String[]{"Square Emerald Cut (3 Step, G85)", "other"});
		DIAMOND_SHAPE_MAP.put(200, new String[]{"Scalloped Edge Round Brilliant (R19)", "round"});
		DIAMOND_SHAPE_MAP.put(201, new String[]{"Shield Brilliant (M01)", "other"});
		DIAMOND_SHAPE_MAP.put(202, new String[]{"Shield Step Cut (M03)", "other"});
		DIAMOND_SHAPE_MAP.put(203, new String[]{"Shield Mixed Cut (M02)", "other"});
		DIAMOND_SHAPE_MAP.put(204, new String[]{"Square Modified Brilliant (G103)", "other"});
		DIAMOND_SHAPE_MAP.put(205, new String[]{"Square Novelty Cut (G84)", "other"});
		DIAMOND_SHAPE_MAP.put(206, new String[]{"Seven-Sided Brilliant (HP1)", "other"});
		DIAMOND_SHAPE_MAP.put(207, new String[]{"Square Step Cut (G01)", "other"});
		DIAMOND_SHAPE_MAP.put(208, new String[]{"Modified Star Brilliant (R15)", "other"});
		DIAMOND_SHAPE_MAP.put(209, new String[]{"Square Mixed Cut (G123)", "other"});
		DIAMOND_SHAPE_MAP.put(210, new String[]{"Tapered Baguette (T03)", "other"});
		DIAMOND_SHAPE_MAP.put(211, new String[]{"Tetrahedral Cut (I35)", "other"});
		DIAMOND_SHAPE_MAP.put(212, new String[]{"Trapezoid Modified Brilliant (T01)", "other"});
		DIAMOND_SHAPE_MAP.put(213, new String[]{"Trapezoid Portrait Cut (T08)", "other"});
		DIAMOND_SHAPE_MAP.put(214, new String[]{"Trapezoid Step Cut (T05)", "other"});
		DIAMOND_SHAPE_MAP.put(215, new String[]{"Triangular Brilliant (I01)", "other"});
		DIAMOND_SHAPE_MAP.put(216, new String[]{"Tree Novelty Cut (R03)", "other"});
		DIAMOND_SHAPE_MAP.put(217, new String[]{"Triangular Mixed Cut (I21)", "other"});
		DIAMOND_SHAPE_MAP.put(218, new String[]{"Triangular Modified Brilliant (I02)", "other"});
		DIAMOND_SHAPE_MAP.put(219, new String[]{"Triangular Portrait Cut (I30)", "other"});
		DIAMOND_SHAPE_MAP.put(220, new String[]{"Triangular Step Cut (I18)", "other"});
	}

	/**
	 * @paparam shapeArray
	 * @return
	 * @throws Exception
	 */
	public static String getDiamondShapeName(String[] shapeArray, Integer index) throws Exception {
		
		String shape = null; 
		if(shapeArray != null && shapeArray.length > 0){
			String shapeType = shapeArray[index];
			
			if(!shapeType.isEmpty()){
				if(index == 1){ 
					//for market value
					if(shapeType.equalsIgnoreCase(CommonConstants.SHAPE_ROUND)){
						shape = CommonConstants.CUT_ROUNDS;
					} else {
						shape = CommonConstants.CUT_FANCIES;
					} 
				} else { 
					//for pdf name
					shape = shapeType;
				}
			}
		}
		return shape;
	}
	
}
