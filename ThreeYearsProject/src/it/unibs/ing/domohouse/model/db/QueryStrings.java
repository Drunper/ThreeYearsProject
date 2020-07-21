package it.unibs.ing.domohouse.model.db;

public class QueryStrings {

	public static final String CHECK_MAINTAINER = "SELECT * FROM manutentore" 
			+ " WHERE nome_manutentore =? AND password =?";
	public static final String CHECK_USER = "SELECT * FROM utente" 
			+ " WHERE nome_utente =?";
	public static final String GET_USER = "SELECT * FROM utente WHERE nome_utente =?";
	public static final String GET_SENSOR_CATEGORIES = "SELECT * FROM categoria_sensori";
	public static final String GET_NUMERIC_INFO_OF_A_CATEGORY = "SELECT informazione_rilevabile.id_informazione, informazione_rilevabile.nome_propriet�, minimo, massimo"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 0 AND nome_categoria_sensori =?";
	public static final String GET_MEASUREMENT_UNIT = "SELECT informazione_rilevabile.id_informazione, informazione_rilevabile.nome_propriet�, unit�_di_misura"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 0 AND nome_categoria_sensori =?";
	public static final String GET_NON_NUMERIC_INFO_OF_A_CATEGORY = "SELECT informazione_rilevabile.id_informazione, informazione_rilevabile.nome_propriet�"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 1 AND nome_categoria_sensori =?";
	public static final String GET_NON_NUMERIC_DOMAIN_VALUE = "SELECT nome_valore FROM valore_dominio"
			+ " WHERE id_informazione =? AND nome_propriet� =?";
	public static final String GET_ACTUATOR_CATEGORIES = "SELECT * FROM categoria_attuatori";
	public static final String GET_OPERATING_MODES_OF_A_CATEGORY = "SELECT nome_modalit� FROM possiede_modalit�"
			+ " WHERE nome_categoria_attuatori =?";
	public static final String GET_USER_HOUSING_UNITS = "SELECT nome_unit�, descrizione, tipo"
			+ " FROM Unit�_immobiliare WHERE nome_utente =?";
	public static final String GET_ROOMS = "SELECT nome_stanza, descrizione FROM stanza"
			+ " WHERE nome_utente =? AND nome_unit� =?";
	public static final String GET_ROOM_PROPERTIES = "SELECT propriet�.nome_propriet�, tipo, valore_di_default"
			+ " FROM propriet� JOIN propriet�_stanze ON propriet�.nome_propriet� = propriet�_stanze.nome_propriet�"
			+ " WHERE nome_utente =? AND nome_unit� =? AND nome_stanza =?";
	public static final String GET_ARTIFACTS = "SELECT nome_artefatto, descrizione FROM artefatto"
			+ " WHERE nome_utente =? AND nome_unit� =? AND posizione =?";
	public static final String GET_ARTIFACT_PROPERTIES = "SELECT propriet�.nome_propriet�, tipo, valore_di_default"
			+ " FROM propriet� JOIN propriet�_artefatti ON propriet�.nome_propriet� = propriet�_artefatti.nome_propriet�"
			+ " WHERE nome_utente =? AND nome_unit� =? AND nome_artefatto =?";
	public static final String GET_SENSORS = "SELECT nome_sensore, stato, stanze_o_artefatti, nome_categoria_sensori"
			+ " FROM sensore WHERE nome_utente =? AND nome_unit� =? AND posizione =?";
	public static final String GET_MEASURED_ARTIFACTS = "SELECT nome_artefatto"
			+ " FROM misura_artefatti JOIN sensore ON (misura_artefatti.nome_utente = sensore.nome_utente AND misura_artefatti.nome_unit� = sensore.nome_unit� AND misura_artefatti.nome_sensore = sensore.nome_sensore)"
			+ " WHERE misura_artefatti.nome_utente =? AND misura_artefatti.nome_unit� =? AND misura_artefatti.nome_sensore =? AND sensore.stanze_o_artefatti = 0";
	public static final String GET_MEASURED_ROOMS = "SELECT nome_stanza"
			+ " FROM misura_stanze JOIN sensore ON (misura_stanze.nome_utente = sensore.nome_utente AND misura_stanze.nome_unit� = sensore.nome_unit� AND misura_stanze.nome_sensore = sensore.nome_sensore)"
			+ " WHERE misura_stanze.nome_utente =? AND misura_stanze.nome_unit� =? AND misura_stanze.nome_sensore =? AND sensore.stanze_o_artefatti = 1";
	public static final String GET_ACTUATORS = "SELECT nome_attuatore, stato, stanze_o_artefatti, nome_categoria_attuatori"
			+ " FROM attuatore WHERE nome_utente =? AND nome_unit� =? AND posizione =?";
	public static final String GET_CONTROLLED_ARTIFACTS = "SELECT nome_artefatto AS nome_oggetto"
			+ " FROM controlla_artefatti JOIN attuatore ON (controlla_artefatti.nome_utente = attuatore.nome_utente AND controlla_artefatti.nome_unit� = attuatore.nome_unit� AND controlla_artefatti.nome_attuatore = attuatore.nome_attuatore)"
			+ " WHERE controlla_artefatti.nome_utente =? AND controlla_artefatti.nome_unit� =? AND controlla_artefatti.nome_attuatore =? AND attuatore.stanze_o_artefatti = 0";
	public static final String GET_CONTROLLED_ROOMS = "SELECT nome_stanza AS nome_oggetto"
			+ " FROM controlla_stanze JOIN attuatore ON (controlla_stanze.nome_utente = attuatore.nome_utente AND controlla_stanze.nome_unit� = attuatore.nome_unit� AND controlla_stanze.nome_attuatore = attuatore.nome_attuatore)"
			+ " WHERE controlla_stanze.nome_utente =? AND controlla_stanze.nome_unit� =? AND controlla_stanze.nome_attuatore =? AND attuatore.stanze_o_artefatti = 1";
	public static final String GET_RULES = "SELECT nome_regola, stato, testo_antecedente, testo_conseguente"
			+ " FROM regola" 
			+ " WHERE nome_utente =? AND nome_unit� =?";
	
	//Query di inserimento
	
	public static final String INSERT_MAINTAINER = "INSERT INTO manutentore (nome_manutentore, password) VALUES (?, ?)";
	
	public static final String INSERT_USER = "INSERT INTO utente (nome_utente) VALUES (?)";
	
	public static final String INSERT_PROPERTY = "INSERT INTO propriet� (nome_propriet�, tipo, valore_default) VALUES (?, ?, ?)";

	public static final String INSERT_NUMERIC_INFO_STRATEGY = "INSERT INTO informazione_rilevabile (nome_propriet�, selettore_informazione, minimo, massimo, unit�_di_misura) VALUES  (?, 0, ?, ?, ?)";

	public static final String INSERT_NON_NUMERIC_INFO_STRATEGY = "INSERT INTO informazione_rilevabile (nome_propriet�, selettore_informazione) VALUES (?, 1)";
	
	public static final String INSERT_SENSOR_CATEGORY = "INSERT INTO categoria_sensori (nome_categoria_sensori, descrizione, sigla, costruttore) VALUES (?, ?, ?, ?);";

	public static final String INSERT_MEASURED_INFO = "INSERT INTO misura_informazioni (nome_categoria_sensori, id_informazione, nome_propriet�)";

	public static final String INSERT_ACTUATOR_CATEGORY = "INSERT INTO categoria_attuatori (nome_categoria_attuatori, descrizione, sigla, costruttore, modalit�_di_default) VALUES (?, ?, ?, ?, ?);";

	public static final String INSERT_OPERATING_MODE = "INSERT INTO modalit�_operativa (nome_modalit�, parametrica) VALUES (?, ?)";

	public static final String INSERT_CATEGORY_OPERATING_MODE = "INSERT INTO possiede_modalit� (nome_categoria_attuatori, nome_modalit�)";

	public static final String INSERT_HOUSING_UNIT = "INSERT INTO unit�_immobiliare (nome_unit�, nome_utente, tipo, descr) VALUES (?, ?, ?, ?)";

	public static final String INSERT_ROOM = "INSERT INTO stanza (nome_stanza, nome_unit�, nome_utente, descr) VALUES (?, ?, ?, ?);";

	public static final String INSERT_ROOM_PROPERTY = "INSERT INTO propriet�_stanze (nome_stanza, nome_unit�, nome_utente, nome_propriet�)";

	public static final String INSERT_ARTIFACT_PROPERTY = "INSERT INTO propriet�_artefatti (nome_artefatto, nome_unit�, nome_utente, nome_propriet�)";
	
	public static final String INSERT_ARTIFACT = "INSERT INTO artefatto (nome_artefatto, nome_unit�, nome_utente, descrizione, posizione) VALUES (?, ?, ?, ?, ?);";

	public static final String INSERT_ACTUATOR = "INSERT INTO attuatore (nome_attuatore, nome_unit�, nome_utente, stato, stanze_o_artefatti, posizione, nome_categoria_attuatori) VALUES (?, ?, ?, ?, ?, ?, ?);";

	public static final String INSERT_ACTUATOR_CONTROL_ROOM = "INSERT INTO controlla_stanze (nome_attuatore, nome_unit�, nome_utente, nome_stanza)";

	public static final String INSERT_ACTUATOR_CONTROL_ARTIFACT = "INSERT INTO controlla_artefatti (nome_attuatore, nome_unit�, nome_utente, nome_artefatto)";

	public static final String INSERT_SENSOR = "INSERT INTO sensore (nome_sensore, nome_unit�, nome_utente, stato, stanze_o_artefatti, posizione, nome_categoria_sensori) VALUES (?, ?, ?, ?, ?, ?, ?);";

	public static final String INSERT_SENSOR_MEASURE_ROOM = "INSERT INTO misura_stanze (nome_sensore, nome_unit�, nome_utente, nome_stanza)";

	public static final String INSERT_SENSOR_MEASURE_ARTIFACT = "INSERT INTO misura_artefatti (nome_sensore, nome_unit�, nome_utente, nome_artefatto)";

	public static final String INSERT_RULE = "INSERT INTO regola (nome_regola, nome_unit�, nome_utente, stato, testo_antecedente, testo_conseguente) VALUES (?, ?, ?, ?, ?, ?)";

	//Query di aggiornamento
	
	public static final String UPDATE_ROOM = "UPDATE stanza SET descrizione = ? WHERE nome_stanza = ? AND nome_unit� = ? AND nome_utente = ?";
	
	public static final String UPDATE_HOUSING_UNIT = "UPDATE unit�_immobiliare SET descrizione = ? WHERE nome_unit� = ? AND nome_utente = ?";
	
	public static final String UPDATE_ARTIFACT = "UPDATE artefatto SET descrizione = ? WHERE nome_artefatto = ? AND nome_unit� = ? AND nome_utente = ?";
	
	//Query di eliminazione
	
	public static final String DELETE_ROOM = "DELETE FROM stanza WHERE nome_stanza = ? AND nome_unit� = ? AND nome_utente = ?";
	
	public static final String DELETE_ACTUATOR = "DELETE FROM actuator WHERE nome_attuatore = ? AND nome_unit� = ? AND nome_utente = ?";
	
	public static final String DELETE_SENSOR = "DELETE FROM sensore WHERE nome_sensore = ? AND nome_unit� = ? AND nome_utente = ?";
	
	public static final String DELETE_ARTIFACT = "DELETE FROM artefatto WHERE nome_artefatto = ? AND nome_unit� = ? AND nome_utente = ?";
	
	public static final String DELETE_RULE = "DELETE FROM regola WHERE nome_regola = ? AND nome_unit� = ? AND nome_utente = ?";
	
	public static final String DELETE_HOUSING_UNIT = "DELETE FROM unit�_immobiliare WHERE nome_unit� = ? AND nome_utente = ?";
	
	public static final String DELETE_USER = "DELETE FROM utente WHERE nome_utente = ?";

	public static final String DELETE_SENSOR_CATEGORY = "DELETE FROM categoria_sensori WHERE nome_categoria_sensori = ?";
	
	public static final String DELETE_ACTUATOR_CATEGORY = "DELETE FROM categoria_attuatori WHERE nome_categoria_attuatori = ?";
	
	//Stringhe di utilit�
	
	public static final String FOUR_VALUES = " VALUES (?, ?, ?, ?)";
	
	public static final String THREE_VALUES = " VALUES (?, ?, ?)";
	
	public static final String TWO_VALUES = " VALUES (?, ?)";
}
