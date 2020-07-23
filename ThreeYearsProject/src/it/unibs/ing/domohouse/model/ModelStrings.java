package it.unibs.ing.domohouse.model;

public class ModelStrings {

	// PATHS, FILELOADER AND FILESAVER
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String DATA_FACADE_PATH = "data";
	public static final String DATA_FACADE_NAME_FILE = FILE_SEPARATOR + "dataFacade.dat";
	public static final String LOG_PATH = "data";
	public static final String LOG_NAME_FILE = FILE_SEPARATOR + "log.txt";
	public static final String LIB_PATH = "data";
	public static final String LIB_NAME = FILE_SEPARATOR + "lib.txt";
	public static final String ERROR_LOAD_FILE = "Non è stato possibile caricare il file! (ERROR: failed or interrupted I/O operations)";
	public static final String ERROR_HELP_FILE = "Errore: File non supportato/non trovato!";
	public static final String CONFIG_FILE_PATH = "data";
	public static final String CONFIG_FILE_NAME = FILE_SEPARATOR + "domohouse.properties";
	public static final String DEFAULT_CLOCK_STRATEGY = "clock.type.class.name=it.unibs.ing.domohouse.model.components.clock.TestClockStrategy";

	// LOGIN
	public static final String SHA_256 = "SHA-256";
	public static final String ALGORITHM_ERROR = "Algorithm not found";

	// DATA OUTPUT
	public static final String SEPARATOR = ":";

	// LOG
	public static final String LOG_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	// INT CONSTANT
	public static final int FIRST_TOKEN = 0;
	public static final int SECOND_TOKEN = 1;
	public static final int THIRD_TOKEN = 2;
	public static final int FOURTH_TOKEN = 3;


	// UTILS AND OTHERS
	public static final String UNDERSCORE = "_";
	public static final String ON = "acceso";
	public static final String OFF = "spento";
	public static final String NULL_CHARACTER = "";
	public static final String CHAR_ZERO = "0";
	public static final String CHAR_POINT = ".";
	public static final String COMMA_WITH_SPACE = ", ";
	public static final String SPACE = " ";
	public static final String OPEN_BRACKET = "(";
	public static final String CLOSED_BRACKET = ")";
	public static final String TO = "-to-";
	public static final String TO_WITH_SPACES = " -to- ";

	// INVARIANT, PRECONDITIONS, POSTCONDITIONS
	public static final String WRONG_INVARIANT = "Invariante della classe non soddisfatto";
	public static final String ACTUATOR_NAME_PRECONDITION = "Il nuovo nome dell'attuatore non contiene caratteri";
	public static final String ELEMENT_MAP_PRECONDITION = "elementMap non contiene ";
	public static final String TO_ADD_PRECONDITION = "toAdd è null";
	public static final String OPERATING_MODE_PRECONDITION = "operatingModesMap non contiene il nome richiesto";
	public static final String PARAMETRIC_OPERATING_MODE_PRECONDITION = "parametricOperatingModesMap non contiene il nome richiesto";
	public static final String ARTIFACT_PRECONDITION = "L'artefatto ottenuto è null";
}
