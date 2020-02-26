package it.unibs.ing.domohouse.util;

public class Strings {
	public static final String DATA_HANDLER_PATH = "data"; 
	public static final String DATA_HANDLER_NAME_FILE = "\\dataHandler.dat";
	public static final String ERROR_LOAD_FILE = "Non è stato possibile caricare il file! (ERROR: failed or interrupted I/O operations)";
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
	
	//INPUTHANDLER
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
	
	//SENSOR CATEGORY
	public static final String SENSOR_CATEGORY_INPUT_NAME = "Inserisci il nome della categoria di sensori";
	public static final String SENSOR_CATEGORY_INPUT_INFO_DOMAIN = "Inserisci il dominio dell'informazione (del tipo \"min-max\" esempio: \"1-20\")";
	public static final String SENSOR_CATEGORY_DETECTABLE_INFO = "Inserisci unità di misura";
	public static final String SENSOR_CATEGORY_INPUT_INFO ="Inserisci l'informazione rilevabile dalla categoria (ad esempio temperatura, pressione, umidità, vento...)";
	public static final String INSERT_SENSOR_CATEGORY_MIN_VALUE = "Inserisci il valore minimo rilevabile della categoria";
	public static final String INSERT_SENSOR_CATEGORY_MAX_VALUE = "Inserisci il valore massimo rilevabile della categoria";
	public static final String NO_CATEGORY_INSERTED = "Attenzione! Devi inserire almeno una categoria!";
	public static final String ERROR_TYPE_CATEGORY = "Attenzione! Il sensore non può appartenere a questo genere di categoria";
	
	//ACTUATOR CATEGORY
	public static final String ACTUATOR_CATEGORY_INPUT_NAME = "Inserisci il nome della categoria di attuatori";
	public static final String ACTUATOR_CATEGORY_INPUT_OPERATING_MODE = "Inserisci una modalità operativa (^ per terminare)";
	public static final String ACTUATOR_CATEGORY_INPUT_DEFAULT_MODE = "Inserisci la modalità di default (tra quelle già inserite)";
	public static final String OPERATING_MODE_NOT_SUPPORTED = "La modalità operativa richiesta non è supportata dal sistema";
	
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
	
	public static int FIRST_TOKEN = 0;
	public static int SECOND_TOKEN = 1;
	public static int THIRD_TOKEN = 2;
	public static int FOURTH_TOKEN = 3;
	public static int FIFTH_TOKEN = 4;
	public static int SPACING_COSTANT = 50;
	
	//UTILS
	public static final String BACK_CHARACTER = "^";
	public static final String WELCOME = "Benvenuto ";
	
	//STATIC MENU VOICES STRINGS
	public static final String [] LOGIN_VOICES = {"Fruitore", "Manutentore"};
	public static final String [] USER_VOICES = {"Visualizzare descrizione unità immobiliare", "Visualizza stanza", 
			"Visualizza categorie di sensori", "Visualizza categorie di attuatori"};
	public static final String [] ROOM_VOICES = {"Visualizzare descrizione stanza", "Visualizza sensore", "Visualizza attuatore", "Visualizza artefatto"};
	public static final String [] MAINTAINER_VOICES = {"Visualizzare descrizione unità immobiliare", "Modifica descrizione unità immobilare", "Visualizza stanza", 
			"Inserisci stanza", "Visualizza categorie di sensori", "Visualizza categorie di attuatori"};
	public static final String [] MAINTAINER_ROOM_VOICES = {"Visualizza descrizione stanza", "Visualizza sensore", "Visualizza attuatore", "Visualizza artefatto", 
			"Modifica descrizione stanza", "Inserisci sensore", "Inserisci attuatore", "Inserisci artefatto"};
	public static final String[] MAINTAINER_UNIT_MENU = {"Visualizza unità immobiliare", "Aggiungi unità immobiliare", "Visualizza categorie di sensori", "Visualizza categorie di attuatori", "Inserisci categoria di sensori", 
			"Inserisci categoria di attuatori", "Salva dati"};
	public static final String[] USER_UNIT_MENU = {"Visualizza unità immobiliare", "Visualizza categorie di sensori", "Visualizza categorie di attuatori"};

}