/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singabytes.ibv3;

import java.util.Vector;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.TagValue;
import com.ib.client.CommissionReport;
// import com.ib.client.UnderComp;   // Comment out for API version 9.72
// Add the following for API version 9.72
import com.ib.client.DeltaNeutralContract;  // Add for API version 9.72
import com.ib.client.EJavaSignal;
import com.ib.client.EReader;
import com.ib.client.EWrapperMsgGenerator;
import com.ib.client.SoftDollarTier;
import java.util.Set;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.*;


// RealTimeData Class is an implementation of the 
// IB API EWrapper class
public class GetHistoricalData implements EWrapper
{
        
    public BufferedWriter writer = null; 
    public String filename = null; 
    // Parameters for the market data request
    public String reqEndDateTime = null;
    public String reqDuration = null;
    public String reqBarSize = null;
    public Contract reqContract = null;
    
    // Add for API 9.72
        private EJavaSignal m_signal = new EJavaSignal();
        private EReader m_reader;
	// Keep track of the next ID
	private int nextOrderID = 0;
	// The IB API Client Socket object
	private EClientSocket client = null;

	public GetHistoricalData ()
	{
        }
        
        private void run() {
            
            // Create a new EClientSocket object API 9.71
            // client = new EClientSocket (this);

            // Create a new EClientSocket object API 9.72
            client = new EClientSocket( this, m_signal);

            // Connect to the TWS or IB Gateway application
            // Leave null for localhost
            // Port Number (should match TWS/IB Gateway configuration)
            client.eConnect (null, 4002, 0);
            // Pause here for connection to complete
              try 
		{
                    System.out.println("Trying to connect...");
                    while (! (client.isConnected()));
			// May also consider checking for nextValidID call here
			// such as:   while (client.NextOrderId <= 0);
		} 
		catch (Exception e) 
		{
                    System.out.println("not connected");
		}

		// API Version 9.72 Launch EReader Thread
		m_reader = new EReader(client, m_signal);
		m_reader.start();
		new Thread() {
				public void run() 
				{
				       processMessages();
				}
		}.start();
		// Create a new contract
		//Contract contract = new Contract ();
		//contract.symbol("EUR");
		//contract.exchange("IDEALPRO");
		//contract.secType("CASH");
		//contract.currency("USD");
		// Create a TagValue list for chartOptions
		Vector<TagValue> chartOptions = new Vector<TagValue>();
		// Make a call to start off data historical retrieval
                System.out.println("Requesting market data with parameters");
                System.out.println("Instrument " + reqContract.symbol());
                System.out.println("End datetime " + reqEndDateTime);
                System.out.println("Duration " + reqDuration);
                System.out.println("BarSize " + reqBarSize);
                
		client.reqHistoricalData(0, reqContract, 
                                         reqEndDateTime,    // End Date/Time
                                         reqDuration,          // Duration (e.g. 2D)
                                         reqBarSize,          // Bar size (e.g. 5 mis)
                                         "MIDPOINT",       // What to show
                                         0,                // useRTH
                                         1,                // dateFormat
                                         chartOptions);
		// You may need to leave off the chartOptions parameter depending on your API version
		// At this point our call is done and any market data events
		// will be returned via the historicalPrice method

    } // end HistoricalData

// processMessages for API version 9.72 only
   private void processMessages() 
   {
	while(true)
	{
		 try {
		  	m_reader.processMsgs();
		 } catch (Exception e) {
			  error(e);
		 }
	} // end while
   } // end processMessages()

    	@Override public void nextValidId(int orderId) {
		
	}
	@Override public void error(Exception e) {
	}

	@Override public void error(int id, int errorCode, String errorMsg) {
	}

	@Override public void connectionClosed() {
	}

	@Override public void error(String str) {
	}

	@Override public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
	}

	@Override public void tickSize(int tickerId, int field, int size) {
	}

	@Override public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {
	}

	@Override public void tickGeneric(int tickerId, int tickType, double value) {
	}

	@Override public void tickString(int tickerId, int tickType, String value) {
	}

	@Override public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureLastTradeDate, double dividendImpact,
			double dividendsToLastTradeDate) {
	}

	@Override public void orderStatus(int orderId, String status, double filled, double remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
	}

	@Override public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
	}

	@Override public void openOrderEnd() {
	}

	@Override public void updateAccountValue(String key, String value, String currency, String accountName) {
	}

	@Override public void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
	}

	@Override public void updateAccountTime(String timeStamp) {
	}

	@Override public void accountDownloadEnd(String accountName) {
	}

	@Override public void contractDetails(int reqId, ContractDetails contractDetails) {
	}

	@Override public void bondContractDetails(int reqId, ContractDetails contractDetails) {
	}

	@Override public void contractDetailsEnd(int reqId) {
	}

	@Override public void execDetails(int reqId, Contract contract, Execution execution) {
	}

	@Override public void execDetailsEnd(int reqId) {
	}

	@Override public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {
	}

	@Override public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size) {
	}

	@Override public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
	}

	@Override public void managedAccounts(String accountsList) {
	}

	@Override public void receiveFA(int faDataType, String xml) {
	}

	@Override public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume, int count, double WAP, boolean hasGaps) {
            System.out.println(date + " open= " + open + " high= " + high + " volume= " + volume + " count= " + count);
            if (writer == null) {
                try {
                    String filename = reqContract.symbol() + "-";
                    filename += reqContract.currency() + "-";
                    filename += reqEndDateTime.replace(" ","").replace(":","") + "-";
                    filename += reqDuration.replace(" ", "") + "-";
                    filename += reqBarSize.replace(" ", "");
                                        
                    String msg = "Create file : " + filename;
                    Logger.getLogger(GetHistoricalData.class.getName()).log(Level.INFO, null, msg);
                    writer = new BufferedWriter(new FileWriter(filename));
                } catch (IOException ex) {
                    Logger.getLogger(GetHistoricalData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                if (!date.contains("finished")/* && date.contains(reqEndDateTime.substring(0,7))*/)
                    writer.write(date + "," + open + "," + high + "," + low + "," + close + "\n");
            } 
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
            if (date.contains("finished")) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(GetHistoricalData.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
	}

	@Override public void scannerParameters(String xml) {
	}

	@Override public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {
	}

	@Override public void scannerDataEnd(int reqId) {
	}

	@Override public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {
	}

	@Override public void currentTime(long time) {
	}

	@Override public void fundamentalData(int reqId, String data) {
	}

	@Override public void deltaNeutralValidation(int reqId, DeltaNeutralContract underComp) {
	}

	@Override public void tickSnapshotEnd(int reqId) {
	}

	@Override public void marketDataType(int reqId, int marketDataType) {
	}

	@Override public void commissionReport(CommissionReport commissionReport) {
	}

	@Override public void position(String account, Contract contract, double pos, double avgCost) {
	}

	@Override public void positionEnd() {
	}

	@Override public void accountSummary(int reqId, String account, String tag, String value, String currency) {
	}

	@Override public void accountSummaryEnd(int reqId) {
	}
	
	@Override public void verifyMessageAPI( String apiData) {
	}

	@Override public void verifyCompleted( boolean isSuccessful, String errorText){
	}

	@Override public void verifyAndAuthMessageAPI( String apiData, String xyzChallenge) {
	}

	@Override public void verifyAndAuthCompleted( boolean isSuccessful, String errorText){
	}

	@Override public void displayGroupList( int reqId, String groups){
	}

	@Override public void displayGroupUpdated( int reqId, String contractInfo){
	}
	
	@Override public void positionMulti( int reqId, String account, String modelCode, Contract contract, double pos, double avgCost) {
	}

	@Override public void positionMultiEnd( int reqId) {
	}

	@Override public void accountUpdateMulti( int reqId, String account, String modelCode, String key, String value, String currency) {
	}

	@Override public void accountUpdateMultiEnd( int reqId) {
	}
	
	public void connectAck() {		
	}

	@Override
	public void securityDefinitionOptionalParameter(int reqId, String exchange, int underlyingConId, String tradingClass,
			String multiplier, Set<String> expirations, Set<Double> strikes) {
		System.out.print(reqId + ", " + exchange + ", " + underlyingConId + ", " + tradingClass + ", " + multiplier);
		
		for (String exp : expirations) {
			System.out.print(", " + exp);
		}
		
		for (double strk : strikes) { 
			System.out.print(", " + strk);
		}
		
		System.out.println();
	}

	@Override
	public void securityDefinitionOptionalParameterEnd(int reqId) {
		System.out.println("done");
	}

	@Override
	public void softDollarTiers(int reqId, SoftDollarTier[] tiers) {
	}


public static void main (String args[])
    {
        // Create an instance
	// At this time a connection will be made
	// and the request for market data will happen
                
        if (args.length > 0) {
            System.out.println(args[0]);
        }
                
        Options options = new Options();

        Option enddatetime = new Option("edt", "enddatetime", true, "Request end datetime [20171115 00:00:00]");
        enddatetime.setRequired(true);
        options.addOption(enddatetime);

        Option duration = new Option("dur", "duration", true, "Request duration [2 D]");
        duration.setRequired(true);
        options.addOption(duration);

        Option barsize = new Option("bar", "barsize", true, "Request bar size [5 mins]");
        barsize.setRequired(true);
        options.addOption(barsize);
        
        Option symbol = new Option("sym", "symbol", true, "Request contract symbol [EUR]");
        symbol.setRequired(true);
        options.addOption(symbol);
        
        Option exchange = new Option("exc", "exchange", true, "Request contract exchange [IDEALPRO]");
        exchange.setRequired(true);
        options.addOption(exchange);
        
        Option secType = new Option("sec", "securitytype", true, "Request contract security type [CASH]");
        secType.setRequired(true);
        options.addOption(secType);
        
        Option currency = new Option("cur", "currency", true, "Request contract currency [USD]");
        currency.setRequired(true);
        options.addOption(currency);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

        // Create a new contract
        Contract reqContract = new Contract ();
	reqContract.symbol(cmd.getOptionValue("symbol"));
	reqContract.exchange(cmd.getOptionValue("exchange"));
	reqContract.secType(cmd.getOptionValue("securitytype"));
	reqContract.currency(cmd.getOptionValue("currency"));

        try {
            GetHistoricalData myData = new GetHistoricalData();
            myData.reqEndDateTime = cmd.getOptionValue("enddatetime");
            myData.reqDuration = cmd.getOptionValue("duration");
            myData.reqBarSize = cmd.getOptionValue("barsize");
            myData.reqContract = reqContract;
            myData.run();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    } // end main
}