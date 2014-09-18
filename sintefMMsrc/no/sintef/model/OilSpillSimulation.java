/**
 * 
 */
package no.sintef.model;


/**
 * @author ubr
 *
 */
public class OilSpillSimulation {

	/**
	 * class representing HDR_Spillinfo record
	 */
	public OilSpillSimulation() {
		
	}

	private int lengthOfDescription;
	private String description;
	private int lengthOfScenarioName;
	private String scenarioName;
	
	/**
	 * lengthOfGridFileName: 0 = use default grids, >0 = use local grids, value = length of filename
	 */
	private int lengthOfGridFileName;
	private String gridFileName;
	private int startHourOfSpill;
	private int startMonthOfSpill;
	private int startDayOfSpill;
	private int startYearOfSpill;
	
	private double spillStartLatitude;
	private double spillStartLongitude; // East from Greenwich (degrees)
	private double releaseDuration; //hours
	
	private int lengthOfWindFileName;
	private String windFileName;
	
	private double totalMassOfComponents; // tons of chemicals
	private int cas1; //chemical abstract service number
	private int cas2; //chemical abstract service number
	private int cas3; //chemical abstract service number
	private int ccode; //chemical classification code
	
	private int lengthOfProfileName;
	private String profileName;
	
	private int lengthOfCurrentFileName;
	private String currentFileName;
	
	private int numberOfCurrentComponents;
	private double[] currentComponents; // not existent if numberOfCurrentComponents is 0
	
	private int tidalHour;
	
	private int cleanUpIndex; // 1=no clean up, 2= linear clean up, 3=response file
	private int lengthOfResponseFileName; // =0 if cleanUpIndex <= 2
	private String responseFileName=""; // if cleanUpIndex = 3
	
	private int airTemperature;
	private int waterTemperature;
	
	private double suspendedSedimentConcentration; // range 0-100,000 mg/l
	private double settlingVelocity; // range 0-100 m/day
	
	private double effectiveConcentration; // ppb for 50% productivity reduction
	private double lethalConcentration1; //ppb for 50% over 96 hours
	private double lethalConcentration2; //ppb for 50% over 96 hours
	private double lethalConcentration3; //ppb for 50% over 96 hours
	private double lethalConcentration4; //ppb for 50% over 96 hours
	
	private double userConcentrationLimit; //lowerConcentration recorded by the physic-chem. fates model

	/**
	 * @return the lengthOfDescription
	 */
	public int getLengthOfDescription() {
		return lengthOfDescription;
	}

	/**
	 * @param i the lengthOfDescription to set
	 */
	public void setLengthOfDescription(int i) {
		this.lengthOfDescription = i;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the lengthOfScenarioName
	 */
	public int getLengthOfScenarioName() {
		return lengthOfScenarioName;
	}

	/**
	 * @param lengthOfScenarioName the lengthOfScenarioName to set
	 */
	public void setLengthOfScenarioName(int lengthOfScenarioName) {
		this.lengthOfScenarioName = lengthOfScenarioName;
	}

	/**
	 * @return the scenarioName
	 */
	public String getScenarioName() {
		return scenarioName;
	}

	/**
	 * @param scenarioName the scenarioName to set
	 */
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	/**
	 * @return the lengthOfGridFileName
	 */
	public int getLengthOfGridFileName() {
		return lengthOfGridFileName;
	}

	/**
	 * @param lengthOfGridFileName the lengthOfGridFileName to set
	 */
	public void setLengthOfGridFileName(int lengthOfGridFileName) {
		this.lengthOfGridFileName = lengthOfGridFileName;
	}

	/**
	 * @return the gridFileName
	 */
	public String getGridFileName() {
		return gridFileName;
	}

	/**
	 * @param gridFileName the gridFileName to set
	 */
	public void setGridFileName(String gridFileName) {
		this.gridFileName = gridFileName;
	}

	/**
	 * @return the startHourOfSpill
	 */
	public int getStartHourOfSpill() {
		return startHourOfSpill;
	}

	/**
	 * @param startHourOfSpill the startHourOfSpill to set
	 */
	public void setStartHourOfSpill(int startHourOfSpill) {
		this.startHourOfSpill = startHourOfSpill;
	}

	/**
	 * @return the startMonthOfSpill
	 */
	public int getStartMonthOfSpill() {
		return startMonthOfSpill;
	}

	/**
	 * @param startMonthOfSpill the startMonthOfSpill to set
	 */
	public void setStartMonthOfSpill(int startMonthOfSpill) {
		this.startMonthOfSpill = startMonthOfSpill;
	}

	/**
	 * @return the startDayOfSpill
	 */
	public int getStartDayOfSpill() {
		return startDayOfSpill;
	}

	/**
	 * @param startDayOfSpill the startDayOfSpill to set
	 */
	public void setStartDayOfSpill(int startDayOfSpill) {
		this.startDayOfSpill = startDayOfSpill;
	}

	/**
	 * @return the startYearOfSpill
	 */
	public int getStartYearOfSpill() {
		return startYearOfSpill;
	}

	/**
	 * @param startYearOfSpill the startYearOfSpill to set
	 */
	public void setStartYearOfSpill(int startYearOfSpill) {
		this.startYearOfSpill = startYearOfSpill;
	}

	/**
	 * @return the spillStartLatitude
	 */
	public double getSpillStartLatitude() {
		return spillStartLatitude;
	}

	/**
	 * @param spillStartLatitude the spillStartLatitude to set
	 */
	public void setSpillStartLatitude(double spillStartLatitude) {
		this.spillStartLatitude = spillStartLatitude;
	}

	/**
	 * @return the spillStartLongitude
	 */
	public double getSpillStartLongitude() {
		return spillStartLongitude;
	}

	/**
	 * @param spillStartLongitude the spillStartLongitude to set
	 */
	public void setSpillStartLongitude(double spillStartLongitude) {
		this.spillStartLongitude = spillStartLongitude;
	}

	/**
	 * @return the releaseDuration
	 */
	public double getReleaseDuration() {
		return releaseDuration;
	}

	/**
	 * @param releaseDuration the releaseDuration to set
	 */
	public void setReleaseDuration(double releaseDuration) {
		this.releaseDuration = releaseDuration;
	}

	/**
	 * @return the lengthOfWindFileName
	 */
	public int getLengthOfWindFileName() {
		return lengthOfWindFileName;
	}

	/**
	 * @param lengthOfWindFileName the lengthOfWindFileName to set
	 */
	public void setLengthOfWindFileName(int lengthOfWindFileName) {
		this.lengthOfWindFileName = lengthOfWindFileName;
	}

	/**
	 * @return the windFileName
	 */
	public String getWindFileName() {
		return windFileName;
	}

	/**
	 * @param windFileName the windFileName to set
	 */
	public void setWindFileName(String windFileName) {
		this.windFileName = windFileName;
	}

	/**
	 * @return the totalMassOfComponents
	 */
	public double getTotalMassOfComponents() {
		return totalMassOfComponents;
	}

	/**
	 * @param totalMassOfComponents the totalMassOfComponents to set
	 */
	public void setTotalMassOfComponents(double totalMassOfComponents) {
		this.totalMassOfComponents = totalMassOfComponents;
	}

	/**
	 * @return the cas1
	 */
	public int getCas1() {
		return cas1;
	}

	/**
	 * @param cas1 the cas1 to set
	 */
	public void setCas1(int cas1) {
		this.cas1 = cas1;
	}

	/**
	 * @return the cas2
	 */
	public int getCas2() {
		return cas2;
	}

	/**
	 * @param cas2 the cas2 to set
	 */
	public void setCas2(int cas2) {
		this.cas2 = cas2;
	}

	/**
	 * @return the cas3
	 */
	public int getCas3() {
		return cas3;
	}

	/**
	 * @param cas3 the cas3 to set
	 */
	public void setCas3(int cas3) {
		this.cas3 = cas3;
	}

	/**
	 * @return the ccode
	 */
	public int getCcode() {
		return ccode;
	}

	/**
	 * @param ccode the ccode to set
	 */
	public void setCcode(int ccode) {
		this.ccode = ccode;
	}

	/**
	 * @return the lengthOfProfileName
	 */
	public int getLengthOfProfileName() {
		return lengthOfProfileName;
	}

	/**
	 * @param lengthOfProfileName the lengthOfProfileName to set
	 */
	public void setLengthOfProfileName(int lengthOfProfileName) {
		this.lengthOfProfileName = lengthOfProfileName;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the lengthOfCurrentFileName
	 */
	public int getLengthOfCurrentFileName() {
		return lengthOfCurrentFileName;
	}

	/**
	 * @param lengthOfCurrentFileName the lengthOfCurrentFileName to set
	 */
	public void setLengthOfCurrentFileName(int lengthOfCurrentFileName) {
		this.lengthOfCurrentFileName = lengthOfCurrentFileName;
	}

	/**
	 * @return the currentFileName
	 */
	public String getCurrentFileName() {
		return currentFileName;
	}

	/**
	 * @param currentFileName the currentFileName to set
	 */
	public void setCurrentFileName(String currentFileName) {
		this.currentFileName = currentFileName;
	}

	/**
	 * @return the numberOfCurrentComponents
	 */
	public int getNumberOfCurrentComponents() {
		return numberOfCurrentComponents;
	}

	/**
	 * @param numberOfCurrentComponents the numberOfCurrentComponents to set
	 */
	public void setNumberOfCurrentComponents(int numberOfCurrentComponents) {
		this.numberOfCurrentComponents = numberOfCurrentComponents;
	}

	/**
	 * @return the currentComponents
	 */
	public double[] getCurrentComponents() {
		return currentComponents;
	}

	/**
	 * @param currentComponents the currentComponents to set
	 */
	public void setCurrentComponents(double[] currentComponents) {
		this.currentComponents = currentComponents;
	}

	/**
	 * @return the tidalHour
	 */
	public int getTidalHour() {
		return tidalHour;
	}

	/**
	 * @param tidalHour the tidalHour to set
	 */
	public void setTidalHour(int tidalHour) {
		this.tidalHour = tidalHour;
	}

	/**
	 * @return the cleanUpIndex
	 */
	public int getCleanUpIndex() {
		return cleanUpIndex;
	}

	/**
	 * @param cleanUpIndex the cleanUpIndex to set
	 */
	public void setCleanUpIndex(int cleanUpIndex) {
		this.cleanUpIndex = cleanUpIndex;
	}

	/**
	 * @return the lengthOfResponseFileName
	 */
	public int getLengthOfResponseFileName() {
		return lengthOfResponseFileName;
	}

	/**
	 * @param lengthOfResponseFileName the lengthOfResponseFileName to set
	 */
	public void setLengthOfResponseFileName(int lengthOfResponseFileName) {
		this.lengthOfResponseFileName = lengthOfResponseFileName;
	}

	/**
	 * @return the responseFileName
	 */
	public String getResponseFileName() {
		return responseFileName;
	}

	/**
	 * @param responseFileName the responseFileName to set
	 */
	public void setResponseFileName(String responseFileName) {
		this.responseFileName = responseFileName;
	}

	/**
	 * @return the airTemperature
	 */
	public int getAirTemperature() {
		return airTemperature;
	}

	/**
	 * @param airTemperature the airTemperature to set
	 */
	public void setAirTemperature(int airTemperature) {
		this.airTemperature = airTemperature;
	}

	/**
	 * @return the waterTemperature
	 */
	public int getWaterTemperature() {
		return waterTemperature;
	}

	/**
	 * @param waterTemperature the waterTemperature to set
	 */
	public void setWaterTemperature(int waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	/**
	 * @return the suspendedSedimentConcentration
	 */
	public double getSuspendedSedimentConcentration() {
		return suspendedSedimentConcentration;
	}

	/**
	 * @param suspendedSedimentConcentration the suspendedSedimentConcentration to set
	 */
	public void setSuspendedSedimentConcentration(
			double suspendedSedimentConcentration) {
		this.suspendedSedimentConcentration = suspendedSedimentConcentration;
	}

	/**
	 * @return the settlingVelocity
	 */
	public double getSettlingVelocity() {
		return settlingVelocity;
	}

	/**
	 * @param settlingVelocity the settlingVelocity to set
	 */
	public void setSettlingVelocity(double settlingVelocity) {
		this.settlingVelocity = settlingVelocity;
	}

	/**
	 * @return the effectiveConcentration
	 */
	public double getEffectiveConcentration() {
		return effectiveConcentration;
	}

	/**
	 * @param effectiveConcentration the effectiveConcentration to set
	 */
	public void setEffectiveConcentration(double effectiveConcentration) {
		this.effectiveConcentration = effectiveConcentration;
	}

	/**
	 * @return the lethalConcentration1
	 */
	public double getLethalConcentration1() {
		return lethalConcentration1;
	}

	/**
	 * @param lethalConcentration1 the lethalConcentration1 to set
	 */
	public void setLethalConcentration1(double lethalConcentration1) {
		this.lethalConcentration1 = lethalConcentration1;
	}

	/**
	 * @return the lethalConcentration2
	 */
	public double getLethalConcentration2() {
		return lethalConcentration2;
	}

	/**
	 * @param lethalConcentration2 the lethalConcentration2 to set
	 */
	public void setLethalConcentration2(double lethalConcentration2) {
		this.lethalConcentration2 = lethalConcentration2;
	}

	/**
	 * @return the lethalConcentration3
	 */
	public double getLethalConcentration3() {
		return lethalConcentration3;
	}

	/**
	 * @param lethalConcentration3 the lethalConcentration3 to set
	 */
	public void setLethalConcentration3(double lethalConcentration3) {
		this.lethalConcentration3 = lethalConcentration3;
	}

	/**
	 * @return the lethalConcentration4
	 */
	public double getLethalConcentration4() {
		return lethalConcentration4;
	}

	/**
	 * @param lethalConcentration4 the lethalConcentration4 to set
	 */
	public void setLethalConcentration4(double lethalConcentration4) {
		this.lethalConcentration4 = lethalConcentration4;
	}

	/**
	 * @return the userConcentrationLimit
	 */
	public double getUserConcentrationLimit() {
		return userConcentrationLimit;
	}

	/**
	 * @param userConcentrationLimit the userConcentrationLimit to set
	 */
	public void setUserConcentrationLimit(double userConcentrationLimit) {
		this.userConcentrationLimit = userConcentrationLimit;
	}
	
	
	
	
	
	
	
	
	
	
	
}
