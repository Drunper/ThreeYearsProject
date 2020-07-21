package it.unibs.ing.domohouse.model.db;

public class QueryStrings {

	public static final String CHECK_MAINTAINER = "SELECT * FROM manutentore" 
			+ " WHERE nome_manutentore =? AND password =?";
	public static final String CHECK_USER = "SELECT * FROM utente" 
			+ " WHERE nome_utente =?";
	public static final String GET_USER = "SELECT * FROM utente WHERE nome_utente =?";
	public static final String GET_SENSOR_CATEGORIES = "SELECT * FROM categoria_sensori";
	public static final String GET_NUMERIC_INFO_OF_A_CATEGORY = "SELECT informazione_rilevabile.id_informazione, informazione_rilevabile.nome_proprietà, minimo, massimo"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 0 AND nome_categoria_sensori =?";
	public static final String GET_MEASUREMENT_UNIT = "SELECT informazione_rilevabile.id_informazione, informazione_rilevabile.nome_proprietà, unità_di_misura"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 0 AND nome_categoria_sensori =?";
	public static final String GET_NON_NUMERIC_INFO_OF_A_CATEGORY = "SELECT informazione_rilevabile.id_informazione, informazione_rilevabile.nome_proprietà"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 1 AND nome_categoria_sensori =?";
	public static final String GET_NON_NUMERIC_DOMAIN_VALUE = "SELECT nome_valore FROM valore_dominio"
			+ " WHERE id_informazione =? AND nome_proprietà =?";
	public static final String GET_ACTUATOR_CATEGORIES = "SELECT * FROM categoria_attuatori";
	public static final String GET_OPERATING_MODES_OF_A_CATEGORY = "SELECT nome_modalità FROM possiede_modalità"
			+ " WHERE nome_categoria_attuatori =?";
	public static final String GET_USER_HOUSING_UNITS = "SELECT nome_unità, descrizione, tipo"
			+ " FROM Unità_immobiliare WHERE nome_utente =?";
	public static final String GET_ROOMS = "SELECT nome_stanza, descrizione FROM stanza"
			+ " WHERE nome_utente =? AND nome_unità =?";
	public static final String GET_ROOM_PROPERTIES = "SELECT proprietà.nome_proprietà, tipo, valore_di_default"
			+ " FROM proprietà JOIN proprietà_stanze ON proprietà.nome_proprietà = proprietà_stanze.nome_proprietà"
			+ " WHERE nome_utente =? AND nome_unità =? AND nome_stanza =?";
	public static final String GET_ARTIFACTS = "SELECT nome_artefatto, descrizione FROM artefatto"
			+ " WHERE nome_utente =? AND nome_unità =? AND posizione =?";
	public static final String GET_ARTIFACT_PROPERTIES = "SELECT proprietà.nome_proprietà, tipo, valore_di_default"
			+ " FROM proprietà JOIN proprietà_artefatti ON proprietà.nome_proprietà = proprietà_artefatti.nome_proprietà"
			+ " WHERE nome_utente =? AND nome_unità =? AND nome_artefatto =?";
	public static final String GET_SENSORS = "SELECT nome_sensore, stato, stanze_o_artefatti, nome_categoria_sensori"
			+ " FROM sensore WHERE nome_utente =? AND nome_unità =? AND posizione =?";
	public static final String GET_MEASURED_ARTIFACTS = "SELECT nome_artefatto"
			+ " FROM misura_artefatti JOIN sensore ON (misura_artefatti.nome_utente = sensore.nome_utente AND misura_artefatti.nome_unità = sensore.nome_unità AND misura_artefatti.nome_sensore = sensore.nome_sensore)"
			+ " WHERE misura_artefatti.nome_utente =? AND misura_artefatti.nome_unità =? AND misura_artefatti.nome_sensore =? AND sensore.stanze_o_artefatti = 0";
	public static final String GET_MEASURED_ROOMS = "SELECT nome_stanza"
			+ " FROM misura_stanze JOIN sensore ON (misura_stanze.nome_utente = sensore.nome_utente AND misura_stanze.nome_unità = sensore.nome_unità AND misura_stanze.nome_sensore = sensore.nome_sensore)"
			+ " WHERE misura_stanze.nome_utente =? AND misura_stanze.nome_unità =? AND misura_stanze.nome_sensore =? AND sensore.stanze_o_artefatti = 1";
	public static final String GET_ACTUATORS = "SELECT nome_attuatore, stato, stanze_o_artefatti, nome_categoria_attuatori"
			+ " FROM attuatore WHERE nome_utente =? AND nome_unità =? AND posizione =?";
	public static final String GET_CONTROLLED_ARTIFACTS = "SELECT nome_artefatto AS nome_oggetto"
			+ " FROM controlla_artefatti JOIN attuatore ON (controlla_artefatti.nome_utente = attuatore.nome_utente AND controlla_artefatti.nome_unità = attuatore.nome_unità AND controlla_artefatti.nome_attuatore = attuatore.nome_attuatore)"
			+ " WHERE controlla_artefatti.nome_utente =? AND controlla_artefatti.nome_unità =? AND controlla_artefatti.nome_attuatore =? AND attuatore.stanze_o_artefatti = 0";
	public static final String GET_CONTROLLED_ROOMS = "SELECT nome_stanza AS nome_oggetto"
			+ " FROM controlla_stanze JOIN attuatore ON (controlla_stanze.nome_utente = attuatore.nome_utente AND controlla_stanze.nome_unità = attuatore.nome_unità AND controlla_stanze.nome_attuatore = attuatore.nome_attuatore)"
			+ " WHERE controlla_stanze.nome_utente =? AND controlla_stanze.nome_unità =? AND controlla_stanze.nome_attuatore =? AND attuatore.stanze_o_artefatti = 1";
	public static final String GET_RULES = "SELECT nome_regola, stato, testo_antecedente, testo_conseguente"
			+ " FROM regola" 
			+ " WHERE nome_utente =? AND nome_unità =?";
	
	//Query di inserimento
	
	public static final String INSERT_MAINTAINER = "INSERT INTO manutentore (nome_manutentore, password) VALUES (?, ?)";
	
	public static final String INSERT_USER = "INSERT INTO utente (nome_utente) VALUES (?)";
	
	public static final String INSERT_PROPERTY = "INSERT INTO proprietà (nome_proprietà, tipo, valore_default) VALUES (?, ?, ?)";

	public static final String INSERT_NUMERIC_INFO_STRATEGY = "INSERT INTO informazione_rilevabile (nome_proprietà, selettore_informazione, minimo, massimo, unità_di_misura) VALUES  (?, 0, ?, ?, ?)";

	public static final String INSERT_NON_NUMERIC_INFO_STRATEGY = "INSERT INTO informazione_rilevabile (nome_proprietà, selettore_informazione) VALUES (?, 1)";
	
	public static final String INSERT_SENSOR_CATEGORY = "INSERT INTO categoria_sensori (nome_categoria_sensori, descrizione, sigla, costruttore) VALUES (?, ?, ?, ?);";

	public static final String INSERT_MEASURED_INFO = "INSERT INTO misura_informazioni (nome_categoria_sensori, id_informazione, nome_proprietà)";

	public static final String INSERT_ACTUATOR_CATEGORY = "INSERT INTO categoria_attuatori (nome_categoria_attuatori, descrizione, sigla, costruttore, modalità_di_default) VALUES (?, ?, ?, ?, ?);";

	public static final String INSERT_OPERATING_MODE = "INSERT INTO modalità_operativa (nome_modalità, parametrica) VALUES (?, ?)";

	public static final String INSERT_CATEGORY_OPERATING_MODE = "INSERT INTO possiede_modalità (nome_categoria_attuatori, nome_modalità)";

	public static final String INSERT_HOUSING_UNIT = "INSERT INTO unità_immobiliare (nome_unità, nome_utente, tipo, descr) VALUES (?, ?, ?, ?)";

	public static final String INSERT_ROOM = "INSERT INTO stanza (nome_stanza, nome_unità, nome_utente, descr) VALUES (?, ?, ?, ?);";

	public static final String INSERT_ROOM_PROPERTY = "INSERT INTO proprietà_stanze (nome_stanza, nome_unità, nome_utente, nome_proprietà)";

	public static final String INSERT_ARTIFACT_PROPERTY = "INSERT INTO proprietà_artefatti (nome_artefatto, nome_unità, nome_utente, nome_proprietà)";
	
	public static final String INSERT_ARTIFACT = "INSERT INTO artefatto (nome_artefatto, nome_unità, nome_utente, descrizione, posizione) VALUES (?, ?, ?, ?, ?);";

	public static final String INSERT_ACTUATOR = "INSERT INTO attuatore (nome_attuatore, nome_unità, nome_utente, stato, stanze_o_artefatti, posizione, nome_categoria_attuatori) VALUES (?, ?, ?, ?, ?, ?, ?);";

	public static final String INSERT_ACTUATOR_CONTROL_ROOM = "INSERT INTO controlla_stanze (nome_attuatore, nome_unità, nome_utente, nome_stanza)";

	public static final String INSERT_ACTUATOR_CONTROL_ARTIFACT = "INSERT INTO controlla_artefatti (nome_attuatore, nome_unità, nome_utente, nome_artefatto)";

	public static final String INSERT_SENSOR = "INSERT INTO sensore (nome_sensore, nome_unità, nome_utente, stato, stanze_o_artefatti, posizione, nome_categoria_sensori) VALUES (?, ?, ?, ?, ?, ?, ?);";

	public static final String INSERT_SENSOR_MEASURE_ROOM = "INSERT INTO misura_stanze (nome_sensore, nome_unità, nome_utente, nome_stanza)";

	public static final String INSERT_SENSOR_MEASURE_ARTIFACT = "INSERT INTO misura_artefatti (nome_sensore, nome_unità, nome_utente, nome_artefatto)";

	public static final String INSERT_RULE = "INSERT INTO regola (nome_regola, nome_unità, nome_utente, stato, testo_antecedente, testo_conseguente) VALUES (?, ?, ?, ?, ?, ?)";

	//Query di aggiornamento
	
	public static final String UPDATE_ROOM = "UPDATE stanza SET descrizione = ? WHERE nome_stanza = ? AND nome_unità = ? AND nome_utente = ?";
	
	public static final String UPDATE_HOUSING_UNIT = "UPDATE unità_immobiliare SET descrizione = ? WHERE nome_unità = ? AND nome_utente = ?";
	
	public static final String UPDATE_ARTIFACT = "UPDATE artefatto SET descrizione = ? WHERE nome_artefatto = ? AND nome_unità = ? AND nome_utente = ?";
	
	//Query di eliminazione
	
	public static final String DELETE_ROOM = "DELETE FROM stanza WHERE nome_stanza = ? AND nome_unità = ? AND nome_utente = ?";
	
	public static final String DELETE_ACTUATOR = "DELETE FROM actuator WHERE nome_attuatore = ? AND nome_unità = ? AND nome_utente = ?";
	
	public static final String DELETE_SENSOR = "DELETE FROM sensore WHERE nome_sensore = ? AND nome_unità = ? AND nome_utente = ?";
	
	public static final String DELETE_ARTIFACT = "DELETE FROM artefatto WHERE nome_artefatto = ? AND nome_unità = ? AND nome_utente = ?";
	
	public static final String DELETE_RULE = "DELETE FROM regola WHERE nome_regola = ? AND nome_unità = ? AND nome_utente = ?";
	
	public static final String DELETE_HOUSING_UNIT = "DELETE FROM unità_immobiliare WHERE nome_unità = ? AND nome_utente = ?";
	
	public static final String DELETE_USER = "DELETE FROM utente WHERE nome_utente = ?";

	public static final String DELETE_SENSOR_CATEGORY = "DELETE FROM categoria_sensori WHERE nome_categoria_sensori = ?";
	
	public static final String DELETE_ACTUATOR_CATEGORY = "DELETE FROM categoria_attuatori WHERE nome_categoria_attuatori = ?";
	
	//Stringhe di utilità
	
	public static final String FOUR_VALUES = " VALUES (?, ?, ?, ?)";
	
	public static final String THREE_VALUES = " VALUES (?, ?, ?)";
	
	public static final String TWO_VALUES = " VALUES (?, ?)";
}
