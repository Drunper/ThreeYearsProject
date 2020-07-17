package it.unibs.ing.domohouse.model.db;

public class QueryStrings {

	public static final String GET_SENSOR_CATEGORIES = "SELECT * FROM categorie_sensori";
	public static final String GET_NUMERIC_INFO_OF_A_CATEGORY = "SELECT id_informazione, nome_propriet�, minimo, massimo"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 0 AND nome_categoria_sensori =?";
	public static final String GET_MEASUREMENT_UNIT = "SELECT id_informazione, nome_propriet�, unit�_di_misura"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 0 AND nome_categoria_sensori =?";
	public static final String GET_NON_NUMERIC_INFO_OF_A_CATEGORY = "SELECT id_informazione, nome_propriet�"
			+ " FROM informazione_rilevabile JOIN misura_informazioni ON informazione_rilevabile.id_informazione = misura_informazioni.id_informazione"
			+ " WHERE selettore_informazione = 1 AND nome_categoria_sensori =?";
	public static final String GET_NON_NUMERIC_DOMAIN_VALUE = "SELECT nome_valore FROM valore_dominio"
			+ " WHERE id_informazione =? AND nome_propriet� =?";
	public static final String GET_ACTUATOR_CATEGORIES = "SELECT * FROM categorie_attuatori";
	public static final String GET_OPERATING_MODES_OF_A_CATEGORY = "SELECT nome_modalit� FROM possiede_modalit�"
			+ " WHERE nome_categoria_attuatori =?";
	public static final String GET_USER_HOUSING_UNITS = "SELECT nome_unit�, descrizione, tipo"
			+ " FROM Unit�_immobiliare WHERE nome_utente =?";
	public static final String GET_ROOMS = "SELECT nome_stanza, descrizione FROM stanza"
			+ " WHERE nome_utente =? AND nome_unit� =?";
	public static final String GET_ROOM_PROPERTIES = "SELECT propriet�.nome_propriet�, tipo, valore_di_default"
			+ " FROM propriet� JOIN propriet�_stanze ON propriet�.nome_propriet� = propriet�_stanze.nome_propriet�"
			+ " WHERE nome_utente =? AND nome_unit� =? AND nome_stanza =?";
	public static final String GET_ARTIFACTS = "SELECT nome_artefatto, descrizione, posizione FROM artefatto"
			+ " WHERE nome_utente =? AND nome_unit� =?";
	public static final String GET_ARTIFACT_PROPERTIES = "SELECT propriet�.nome_propriet�, tipo, valore_di_default"
			+ " FROM propriet� JOIN propriet�_artefatti ON propriet�.nome_propriet� = propriet�_artefatti.nome_propriet�"
			+ " WHERE nome_utente =? AND nome_unit� =? AND nome_stanza =?";
	public static final String GET_SENSORS = "SELECT nome_sensore, stato, stanze_o_artefatti, nome_categoria_sensori, posizione"
			+ " FROM sensore WHERE nome_utente =? AND nome_unit� =?";
	public static final String GET_MEASURED_OBJECTS = "SELECT nome_artefatto AS nome_oggetto"
			+ " FROM misura_artefatti JOIN sensore ON (misura_artefatti.nome_utente = sensore.nome_utente AND misura_artefatti.nome_unit� = sensore.nome_unit� AND misura_artefatti.nome_sensore = sensore.nome_sensore)"
			+ " WHERE misura_artefatti.nome_utente =? AND misura_artefatti.nome_unit� =? AND misura_artefatti.nome_sensore =? AND sensore.stanze_o_artefatti = 0"
			+ " UNION" + " SELECT nome_stanza AS nome_oggetto"
			+ " FROM misura_stanze JOIN sensore ON (misura_stanze.nome_utente = sensore.nome_utente AND misura_stanze.nome_unit� = sensore.nome_unit� AND misura_stanze.nome_sensore = sensore.nome_sensore)"
			+ " WHERE misura_stanze.nome_utente =? AND misura_stanze.nome_unit� =? AND misura_stanze.nome_sensore =? AND sensore.stanze_o_artefatti = 1";
	public static final String GET_ACTUATORS = "SELECT nome_attuatore, stato, stanze_o_artefatti, nome_categoria_attuatori, posizione"
			+ " FROM attuatore WHERE nome_utente =? AND nome_unit� =?";
	public static final String GET_CONTROLLED_OBJECTS = "SELECT nome_artefatto AS nome_oggetto"
			+ " FROM controlla_artefatti JOIN attuatore ON (controlla_artefatti.nome_utente = attuatore.nome_utente AND controlla_artefatti.nome_unit� = attuatore.nome_unit� AND controlla_artefatti.nome_attuatore = attuatore.nome_attuatore)"
			+ " WHERE controlla_artefatti.nome_utente =? AND controlla_artefatti.nome_unit� =? AND controlla_artefatti.nome_attuatore =? AND attuatore.stanze_o_artefatti = 0"
			+ " UNION" + " SELECT nome_stanza AS nome_oggetto"
			+ " FROM controlla_stanze JOIN sensore ON (controlla_stanze.nome_utente = attuatore.nome_utente AND controlla_stanze.nome_unit� = attuatore.nome_unit� AND controlla_stanze.nome_attuatore = attuatore.nome_attuatore)"
			+ " WHERE controlla_stanze.nome_utente =? AND controlla_stanze.nome_unit� =? AND controlla_stanze.nome_attuatore =? AND attuatore.stanze_o_artefatti = 1";
	public static final String GET_RULES = "SELECT nome_regola, stato, testo_antecedente, testo_conseguente"
			+ " FROM regola" 
			+ " WHERE nome_utente =? AND nome_unit� =?";
}
