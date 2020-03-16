package it.unibs.ing.domohouse.util;

public class Strings {
	//PATHS, FILELOADER AND FILESAVER
	public static final String DATA_HANDLER_PATH = "data"; 
	public static final String DATA_HANDLER_NAME_FILE = "\\dataHandler.dat";
	public static final String HELP_PATH = "data";
	public static final String USER_HELP_FILE_NAME = "\\User_Guide.pdf";
	public static final String MAINTAINER_HELP_FILE_NAME = "\\Maintainer_Guide.pdf";
	public static final String LOG_PATH = "data";
	public static final String LOG_NAME_FILE = "\\log.txt";
	public static final String LIB_PATH = "data";
	public static final String LIB_NAME = "\\lib.txt";
	public static final String ERROR_LOAD_FILE = "Non è stato possibile caricare il file! (ERROR: failed or interrupted I/O operations)";
	public static final String ERROR_HELP_FILE = "Errore: File non supportato/non trovato!";
	public static final String LOADING_FILE = "Caricamento file...";
	public static final String LOADED = "Caricamento da file effettuato!";
	public static final String NO_FILE = "Attenzione! Non è stato trovato alcun file di salvataggio. Chiamare un manutentore per configurare il sistema!";
	public static final String BASIC_FILE_CREATION = "Creazione dati di base per la prima configurazione...";
	public static final String USER_FIRST_ACCESS_PROHIBITED = "Accesso vietato! Per il primo avvio chiamare un manutentore per configurare il sistema";
	public static final String DATA_SAVED = "Dati salvati correttamente nel sistema!";
	
	//TITLES
	public static final String LOGIN_MENU_TITLE = "Login:";
	public static final String USER_MENU_TITLE = "Menu unità immobiliare";
	public static final String USER_ROOM_MENU_TITLE = "Menu stanza";
	public static final String USER_UNIT_MENU_TITLE = "Menu principale fruitore";
	public static final String MAINTAINER_MENU_TITLE = "Menu unità immobiliare (manutentore)";
	public static final String MAINTAINER_ROOM_MENU_TITLE = "Menu stanza (manutentore)";
	public static final String MAINTAINER_UNIT_MENU_TITLE = "Menu principale manutentore";
	
	//LOGIN
	public static final String INSERT_PASSWORD = "Inserisci la password";
	public static final String USER_OR_PASSWORD_ERROR = "Nome utente o password errati";
	public static final String INSERT_USER = "Inserisci il nome utente (^ per tornare indietro)";
	public static final String PASSWORD = "6fcb473c563dc49628a187d2a590ff2c000da215d8cd914f7901df3bc2a2c626"; //pippo123456
	public static final String MAINTAINER_USER = "prova";
	public static final String MAINTAINER = "Il manutentore ";
	public static final String SHA_256 = "SHA-256";
	public static final String ALGORITHM_ERROR = "Algorithm not found";
	
	//MAINTAINER MENU
	public static final String INSERT_HOUSE = "Inserisci il nome della casa su cui vuoi operare";
	public static final String ERROR_NON_EXISTENT_HOUSE = "Attenzione! La casa selezionata non esiste, assicurati di aver digitato correttamente il nome.";
	public static final String NO_HOUSE = "Non sono presenti unità immobiliari nel sistema. E' necessario l'intervento del manutentore.";
	
	//HOUSE MENU
	public static final String INSERT_ROOM = "Inserisci il nome della stanza su cui vuoi operare";
	public static final String NO_ROOM = "Non sono ancora presenti stanze nella casa";
	public static final String ERROR_NON_EXISTENT_ROOM = "Attenzione! La stanza selezionata non esite, assicurati di aver digitato correttamente il nome.";
	public static final String INSERT_SENSOR_CATEGORY = "Inserisci la categoria di sensori che vuoi visualizzare";
	public static final String NO_SENSOR_CATEGORY = "Non sono ancora presenti categori di sensori";
	public static final String ERROR_NON_EXISTENT_SENSOR_CATEGORY = "Attenzione! La categoria di sensori selezionata non esiste.";
	public static final String INSERT_ACTUATOR_CATEGORY = "Inserisci la categoria di attuatori che vuoi visualizzare";
	public static final String NO_ACTUATOR_CATEGORY = "Non sono ancora presenti categorie di attuatori";
	public static final String ERROR_NON_EXISTENT_ACTUATOR_CATEGORY = "Attenzione! La categoria di attuatori selezionata non esiste.";
	public static final String SENSOR_CATEGORY_CHOICE = "Vuoi creare una categoria di sensori numerica? (No la creerà non numerica)";
	
	//ROOM MENU
	public static final String INSERT_SENSOR = "Inserisci il nome del sensore che vuoi visualizzare";
	public static final String NO_SENSOR = "Non sono presenti sensori in questa stanza";
	public static final String ERROR_NON_EXISTENT_SENSOR = "Attenzione! Il sensore selezionato non esiste.";
	public static final String INSERT_ACTUATOR = "Inserisci il nome dell'attuatore che vuoi visualizzare";
	public static final String NO_ACTUATOR = "Non sono presenti attuatori in questa stanza";
	public static final String ERROR_NON_EXISTENT_ACTUATOR = "Attenzione! L'attuatore selezionato non esiste.";
	public static final String INSERT_ARTIFACT = "Inserisci il nome dell'artefatto che vuoi visualizzare";
	public static final String NO_ARTIFACT = "Non sono presenti artefatti in questa stanza";
	public static final String ERROR_NON_EXISTENT_ARTIFACT = "Attenzione! L'artefatto selezionato non esiste.";
	public static final String ROOM_INPUT_TEMPERATURE = "Inserisci valore temperatura della stanza (gradi)";
	public static final String ROOM_INPUT_HUMIDITY = "Inserisci valore umidita della stanza della stanza";
	public static final String ROOM_INPUT_PRESSURE = "Inserisci valore pressione della stanza della stanza (hPa)";
	public static final String ROOM_INPUT_WIND = "Inserisci valore velocità del vento (km/h)";
	
	//GENERIC INPUTHANDLER
	public static final String PROCEED_WITH_CREATION = "Procedere con la creazione e salvataggio?";
	public static final String NAME_ALREADY_EXISTENT = "Nome già inserito, prego reinserire altro nome";
	public static final String PROCEED_WITH_SAVING = "Salvare le modifiche?";
	public static final String INSERT_CATEGORY = "Inserisci il nome della categoria (\"^\" per fermarti)";
	public static final String CATEGORY_NON_EXISTENT = "Categoria non esistente, prego reinserire altro nome";
	public static final String INPUT_CATEGORY_ABBREVIATION = "Inserisci la sigla della categoria";
	public static final String INPUT_CATEGORY_MANUFACTURER = "Inserisci il costruttore";
	public static final String INPUT_NON_NUMERIC_DOMAIN = "Inserisci un'informazione di dominio (\"^\" per uscire)";
	
	//HOUSE
	public static final String HOUSE_INPUT_NAME = "Inserisci il nome dell'unità immobiliare";
	public static final String HOUSE_INPUT_DESCRIPTION = "Inserisci la descrizione dell'unità immobiliare";
	
	//ROOM
	public static final String ROOM_INPUT_NAME = "Inserisci il nome della stanza";
	public static final String ROOM_INPUT_DESCRIPTION = "Inserisci la descrizione della stanza";
	
	//ARTIFACT
	public static final String ARTIFACT_INPUT_NAME = "Inserisci il nome dell'artefatto";
	public static final String ARTIFACT_ROOM_NAME_ASSIGNED = "Nome già assegnato a stanza/artefatto, prego reinserire altro nome";
	public static final String ARTIFACT_INPUT_DESCRIPTION = "Inserisci la descrizione dell'artefatto";
	public static final String ROOM_OR_ARTIFACT_NON_EXISTENT = "Artefatto/stanza non presente, prego reinserire";
	
	//SENSOR
	public static final String SENSOR_CHOICE = "Vuoi inserire un sensore numerico? (No inserirà un sensore non numerico";
	public static final String SENSOR_INPUT_NAME = "Inserisci il nome del sensore (senza la categoria)";
	public static final String SENSOR_NAME_ASSIGNED = "Nome già assegnato a un sensore, prego reinserire altro nome";
	public static final String SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION = "Vuoi associare il sensore a stanze?(No assocerà il sensore ad artefatti)";
	public static final String SENSOR_ROOM_ASSOCIATION = "Inserisci il nome della stanza da associare al sensore";
	public static final String SENSOR_ARTIFACT_ASSOCIATION = "Inserisci il nome dell'artefatto da associare al sensore";
	public static final String SENSOR_WRONG_ASSOCIATION_ROOM = "L'elemento a cui si vuole associare il sensore non è una stanza!";
	public static final String SENSOR_WRONG_ASSOCIATION_ARTIFACT = "L'elemento a cui si vuole associare il sensore non è un artefatto!";
	public static final String SENSOR_WRONG_ASSOCIATION_CATEGORY = "Un sensore di questa categoria è già associato all'artefatto/stanza in questione";
	public static final String SENSOR_ANOTHER_ASSOCIATION = "Associare sensore ad altro oggetto?";
	public static final String NO_ASSOCIABLE_ELEMENT = "Non ci sono oggetti associabili di questo tipo";
	public static final String NO_SENSOR_ROOM_OR_ARTIFACT_ERROR = "Impossibile creare il sensore perchè non ci sono stanze o artefatti associabili ad esso";
	public static final String NO_SENSOR_CATEGORY_ERROR = "Impossibile creare il sensore perchè non sono presenti categorie di quel tipo nel sistema";
	
	//ACTUATOR
	public static final String ACTUATOR_INPUT_NAME = "Inserisci il nome dell'attuatore (senza la categoria)";
	public static final String ACTUATOR_NAME_ASSIGNED = "Nome già assegnato a un attuatore, prego reinserire altro nome";
	public static final String ACTUATOR_ARTIFACT_OR_ROOM_ASSOCIATION = "Vuoi associare l'attuatore a stanze?(No assocerà il sensore ad artefatti)";
	public static final String ACTUATOR_ROOM_ASSOCIATION = "Inserisci il nome della stanza da associare all'attuatore";
	public static final String ACTUATOR_ARTIFACT_ASSOCIATION = "Inserisci il nome dell'artefatto da associare all'attuatore";
	public static final String ACTUATOR_WRONG_ASSOCIATION_ROOM = "L'elemento a cui si vuole associare l'attuatore non è una stanza!";
	public static final String ACTUATOR_WRONG_ASSOCIATION_ARTIFACT = "L'elemento a cui si vuole associare l'attautore non è un artefatto!";
	public static final String ACTUATOR_WRONG_ASSOCIATION_CATEGORY = "Un attuatore di questa categoria è già associato all'artefatto/stanza in questione";
	public static final String ACTUATOR_ANOTHER_ASSOCIATION = "Associare attuatore ad altro oggetto?";
	public static final String NO_ACTUATOR_ROOM_OR_ARTIFACT_ERROR = "Impossibile creare l'attuatore perchè non ci sono stanze o artefatti associabili ad esso";
	public static final String NO_ACTUATOR_CATEGORY_ERROR = "Impossibile creare l'attuatore perchè non sono presenti categorie nel sistema";
	public static final String NO_ROOM_TO_ASSOC = "Non sono disponibili stanze da associare con quella categoria";
	public static final String NO_ARTIFACT_TO_ASSOC = "Non sono disponibili artefatti da associare con quella categoria";
	
	//SENSOR CATEGORY
	public static final String SENSOR_CATEGORY_INPUT_NAME = "Inserisci il nome della categoria di sensori";
	public static final String SENSOR_CATEGORY_INPUT_INFO_DOMAIN = "Inserisci il dominio dell'informazione (del tipo \"min-max\" esempio: \"1-20\")";
	public static final String SENSOR_CATEGORY_DETECTABLE_INFO = "Inserisci unità di misura";
	public static final String SENSOR_CATEGORY_INPUT_INFO ="Inserisci l'informazione rilevabile dalla categoria (ad esempio temperatura, pressione, umidità, vento, presenza...)";
	public static final String INSERT_SENSOR_CATEGORY_MIN_VALUE = "Inserisci il valore minimo rilevabile della categoria";
	public static final String INSERT_SENSOR_CATEGORY_MAX_VALUE = "Inserisci il valore massimo rilevabile della categoria";
	public static final String NO_CATEGORY_INSERTED = "Attenzione! Devi inserire almeno una categoria!";
	public static final String ERROR_TYPE_CATEGORY = "Attenzione! Il sensore non può appartenere a questo genere di categoria";
	
	//ACTUATOR CATEGORY
	public static final String ACTUATOR_CATEGORY_INPUT_NAME = "Inserisci il nome della categoria di attuatori";
	public static final String ACTUATOR_CATEGORY_INPUT_OPERATING_MODE = "Inserisci una modalità operativa (^ per terminare)";
	public static final String ACTUATOR_CATEGORY_INPUT_DEFAULT_MODE = "Inserisci la modalità di default (tra quelle già inserite)";
	public static final String OPERATING_MODE_NOT_SUPPORTED = "La modalità operativa richiesta non è supportata dal sistema";
	public static final String INSERT_OPERATING_MODE = "Inserisci la modalità operativa";
	public static final String ERR_OPERATING_MODE = "La modalità operativa inserita è inesistente";
	
	//RULE INPUT
	public static final String INPUT_RULE_NAME = "Inserisci il nome della regola";
	public static final String INPUT_INFO_TO_DETECT = "Inserisci l'informazione da rilevare";
	public static final String ERROR_INFO_NAME = "Inserisci il nome corretto dell'informazione da rilevare";
	public static final String INPUT_OPERATOR = "Inserisci l'operatore";
	public static final String ERROR_OPERATOR = "Inserisci un operatore valido";
	public static final String INPUT_DESIRED_VALUE = "Inserisci il valore desiderato da confrontare";
	public static final String INPUT_NEW_COST = "Vuoi inserire un altro costituente?";
	public static final String INPUT_COST_OPERATOR = "Inserisci l'operatore ( && oppure || )";
	public static final String INPUT_OPERATING_MODE = "Inserisci la modalità operativa che verrà azionata";
	public static final String ERROR_OPERATING_MODE = "Modalità operativa non presente";
	public static final String INPUT_PARAMETER_VALUE = "Inserisci il valore del parametro";
	public static final String INPUT_STRING_PARAMETER_VALUE = "Inserisci il valore del parametro (String)";
	
	//DATA OUTPUT
	public static final String SEPARATOR = ":";
	public static final String SEPARATOR_WITH_SPACE = ": ";
	public static final String NAME = "Nome: ";
	public static final String HOUSING_UNIT = "Unità immobiliare";
	public static final String ROOM = "Stanza";
	public static final String ARTIFACT = "Artefatto";
	public static final String ACTUATOR = "Attuatore";
	public static final String SENSOR = "Sensore";
	public static final String ACTUATOR_CATEGORY = "Categoria di attuatori";
	public static final String SENSOR_CATEGORY = "Categoria di sensori";
	public static final String CATEGORY = "Categoria: ";
	public static final String DESCRIPTION = "Descrizione: ";
	public static final String ROOMS_AVAILABLE = "Sono presenti le seguenti stanze:";
	public static final String ELEMENTS_AVAILABLE = "Nella stanza sono presenti i seguenti elementi:";
	public static final String CONTROLLED_ELEMENTS = "Lista delle stanze o artefatti controllati:";
	public static final String MEASURED_ELEMENTS = "Lista delle stanze o artefatti misurati:";
	public static final String OPERATING_MODE = "Modalità operativa: ";
	public static final String STATUS = "Stato: ";
	public static final String CONTROLLED_ELEMENT_TAG = "ce";
	public static final String MEASURED_ELEMENT_TAG = "me";
	public static final String MEASURED_VALUE_TAG = "mv";
	public static final String LAST_MEASURED_VALUE = "Ultimo valore rilevato: ";
	public static final String ABBREVATION = "Sigla: ";
	public static final String MANUFACTURER = "Costruttore: ";
	public static final String INFO_DOMAIN = "Dominio_info_";
	public static final String END_DOMAIN_TAG = "endD";
	public static final String DETECTABLE_INFOS = "Informazioni rilevabili:";
	public static final String DEFAULT_MODE = "Modalità di default: ";
	public static final String OPERATING_MODES = "Modalità operative:";
	public static final String SUCCESS_IMPORT_FILE = "File di libreria importato correttamente!";
	
	//LOG STRINGS
	public static final String LOG_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String LOG_SHOW_MAIN_MENU = "Visualizzazione del menù principale";
	public static final String LOG_FIRST_ACCESS = "Primo accesso al programma";
	public static final String LOG_SYSTEM_ACCESS = " ha effettuato l'accesso al sistema";
	public static final String LOG_BASIC_FILE_CREATION = "Creazione dei file di base";
	public static final String LOG_EXIT_MENU = "Uscita dal menù corrente";
	public static final String LOG_SHOW_USER_MENU = "Visualizzazione del menù principale Fruitore";
	public static final String LOG_SHOW_MAINTAINTER_MENU = "Visualizzazione del menù principale Manutentore";
	public static final String LOG_INSERT_HOUSE = "Inserimento di una nuova unità immobiliare...";
	public static final String LOG_INSERT_SENSOR_CATEGORY = "Inserimento di una nuova categoria di sensori...";
	public static final String LOG_INSERT_ACTUATOR_CATEGORY = "Inserimento di una nuova categoria di attuatori...";
	public static final String LOG_INSERT_SENSOR_CATEGORY_SUCCESS = "Inserimento di una nuova categoria di sensori avvenuta correttamente";
	public static final String LOG_INSERT_ACTUATOR_CATEGORY_SUCCESS = "Inserimento di una nuova categoria di attuatori avvenuta correttamente";
	public static final String LOG_SAVING_DATA = "Salvataggio dei dati ...";
	public static final String LOG_IMPORTING_FILE = "Importazione file...";
	public static final String LOG_ERROR_IMPORT = "Errore riscontrato : ";
	public static final String LOG_SHOW_MAINTAINTER_UNIT_MENU = "Visualizzazione del menù dell'unità immobiliare (versione Manutentore) ";
	public static final String LOG_INSERT_HOUSE_SUCCESS = "Inserimento di una nuova unità immobiliare avvenuto correttamente";
	public static final String LOG_DESCR_HOUSE = "Visualizzazione della descrizione dell'unità immobiliare";
	public static final String LOG_CHANGE_DESCR_HOUSE = "Visualizzazione della descrizione dell'unità immobiliare";
	public static final String LOG_SHOW_MAINTAINTER_ROOM_MENU = "Visualizzazione del menu della stanza (Versione Manutentore) ";
	public static final String LOG_SHOW_USER_ROOM_MENU = "Visualizzazione del menu della stanza (Versione Fruitore) ";
	public static final String LOG_INSERT_ROOM = "Inserimento di una nuova stanza...";
	public static final String LOG_INSERT_ROOM_SUCCESS = "Inserimento della nuova stanza avvenuto correttamente!";
	public static final String LOG_SHOW_SENSOR_CATEGORY = "Visualizzazione della categoria di sensore ";
	public static final String LOG_SHOW_ACTUATOR_CATEGORY = "Visualizzazione della categoria di attuatore ";
	public static final String LOG_INSERT_NEW_RULE = "Inserimento di una nuova regola...";
	public static final String LOG_INSERT_NEW_RULE_SUCCESS = "Inserimento della nuova regola avvenuto correttamnete";
	public static final String LOG_SHOW_ENABLED_RULES = "Visualizzazione delle regole attive";
	public static final String LOG_SHOW_ALL_RULES = "Visualizzazione di tutte le regole";
	public static final String LOG_ENABLE_DISABLE_RULE = "Attivazione o disattivazione di una regola";
	public static final String LOG_REFRESH_HOUR = "Aggiornamento della visualizzazione dell'orario";
	public static final String LOG_SHOW_USER_UNIT_MENU = "Visualizzazione del menu dell'unità immobiliare (Versione Fruitore) ";
	public static final String LOG_SHOW_DESCR_ROOM = "Visualazzazione della descrizioned della stanza ";
	public static final String LOG_SHOW_SENSOR = "Visualizzazione del sensore ";
	public static final String LOG_SHOW_ACTUATOR = "Visualizzazione dell'attuatore ";
	public static final String LOG_SHOW_ARTIFACT = "Visualizzazione dell'artefatto ";
	public static final String LOG_ACTUATOR_ACTION = "Azionamento dell'attuatore ";
	public static final String LOG_ENABLE_DISABLE_DISP = "Attivazione o disattivazione del dispositivo";
	public static final String LOG_CHANGE_ROOM_DESCR = "Modifica della descrizione della stanza ";
	public static final String LOG_INSERT_SENSOR = "Inserimento di un nuovo sensore...";
	public static final String LOG_INSERT_SENSOR_SUCCESS = "Inserimento di un nuovo sensore avvenuto correttamente";
	public static final String LOG_INSERT_ACTUATOR = "Inserimento di un nuovo attuatore...";
	public static final String LOG_INSERT_ACTUATOR_SUCCESS = "Inserimento di un nuovo attuatore avvenuto correttamente";
	public static final String LOG_INSERT_ARTIFACT = "Inserimento di un nuovo artefatto...";
	public static final String LOG_INSERT_ARTIFACT_SUCCESS = "Inserimento di un nuovo artefatto avvenuto correttamente";
	
	//INT CONSTANT
	public static int FIRST_TOKEN = 0;
	public static int SECOND_TOKEN = 1;
	public static int THIRD_TOKEN = 2;
	public static int FOURTH_TOKEN = 3;
	public static int FIFTH_TOKEN = 4;
	public static int SPACING_COSTANT = 50;
	
	//UTILS AND OTHERS
	public static final String BACK_CHARACTER = "^";
	public static final String UNDERSCORE = "_";
	public static final String WELCOME = "Benvenuto ";
	public static final String ON = "acceso";
	public static final String OFF = "spento";
	public static final String NULL_CHARACTER = "";
	public static final String CHAR_ZERO = "0";
	public static final String CHAR_POINT = ".";
	public static final String ARROW = " -> ";
	public static final String COMMA_WITH_SPACE = ", ";
	public static final String SPACE = " ";
	public static final String OPEN_BRACKET = "(";
	public static final String CLOSED_BRACKET = ")";
	public static final String TO = "-to-";
	public static final String TO_WITH_SPACES = " -to- ";
	
	//INVARIANT, PRECONDITIONS, POSTCONDITIONS
	public static final String WRONG_INVARIANT = "Invariante della classe non soddisfatto";
	public static final String ACTUATOR_NAME_PRECONDITION = "Il nuovo nome dell'attuatore non contiene caratteri";
	public static final String ELEMENT_MAP_PRECONDITION = "elementMap non contiene ";
	public static final String TO_ADD_PRECONDITION = "toAdd è null";
	public static final String UNFORMATTED_TEXT_PRECONDITION = "unformattedText non contiene informazioni riguardo allo stato";
	public static final String OPERATING_MODE_PRECONDITION = "operatingModesMap non contiene il nome richiesto";
	public static final String PARAMETRIC_OPERATING_MODE_PRECONDITION = "parametricOperatingModesMap non contiene il nome richiesto";
	public static final String NUMERIC_PROPERTIES_PRECONDITION = "numericPropertiesMap non contiene ";
	public static final String NON_NUMERIC_PROPERTIES_PRECONDITION = "nonNumericPropertiesMap non contiene ";
	public static final String ARTIFACT_PRECONDITION = "L'artefatto ottenuto è null";
	public static final String DOMAIN_PRECONDITION_1 = "domain non contiene \"(\" ";
	public static final String DOMAIN_PRECONDITION_2 = "info non contiene \"to\" ";
	public static final String DOMAIN_PRECONDITION_3 = "domain non contiene \")\" ";
	
	//MENU VOICES STRINGS
	public static final String [] LOGIN_VOICES = {"Fruitore", "Manutentore"};
	public static final String [] USER_VOICES = {"Visualizzare descrizione unità immobiliare", "Visualizza stanza", 
			"Visualizza categorie di sensori", "Visualizza categorie di attuatori", "Aggiungi regola", "Visualizza regole attive", "Visualizza tutte le regole", "Attiva/disattiva regola", "Aggiorna ora"};
	public static final String [] ROOM_VOICES = {"Visualizzare descrizione stanza", "Visualizza sensore", "Visualizza attuatore", "Aziona attuatore", "Visualizza artefatto", "Attiva/disattiva dispositivo", "Aggiorna ora"};
	public static final String [] MAINTAINER_VOICES = {"Visualizzare descrizione unità immobiliare", "Modifica descrizione unità immobilare", "Visualizza stanza", 
			"Inserisci stanza", "Visualizza categorie di sensori", "Visualizza categorie di attuatori", "Aggiungi regola", "Visualizza regole attive", "Visualizza tutte le regole", "Attiva/disattiva regola", "Aggiorna ora"};
	public static final String [] MAINTAINER_ROOM_VOICES = {"Visualizza descrizione stanza", "Visualizza sensore", "Visualizza attuatore", "Aziona attuatore", "Visualizza artefatto", 
			"Modifica descrizione stanza", "Inserisci sensore", "Inserisci attuatore", "Inserisci artefatto", "Attiva/disattiva dispositivo", "Aggiorna ora"};
	public static final String[] MAINTAINER_UNIT_MENU = {"Visualizza unità immobiliare", "Aggiungi unità immobiliare", "Visualizza categorie di sensori", "Visualizza categorie di attuatori", "Inserisci categoria di sensori", 
			"Inserisci categoria di attuatori", "Salva dati", "Importa file di libreria", "Apri Help", "Visualizza Log", "Aggiorna ora"};
	public static final String[] USER_UNIT_MENU = {"Visualizza unità immobiliare", "Visualizza categorie di sensori", "Visualizza categorie di attuatori", "Apri Help", "Aggiorna ora"};
}