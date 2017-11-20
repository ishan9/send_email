package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.util.Duration;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;


public class Controller implements Initializable {

    @FXML
    private JFXTextField textFieldFromAddress;

    @FXML
    private JFXTextField textFieldToAddress;

    @FXML
    private JFXTextField textFieldServer;

    @FXML
    private JFXTextField textFieldMailSubject;

    @FXML
    private JFXButton buttonSend;

    @FXML
    private AnchorPane anchorComposeMail;

    @FXML
    private JFXTextArea textAreaMailBody;

    @FXML
    private HBox hBoxMailBodyOptions;

    @FXML
    private JFXRadioButton radioButtonSendExistingMail;

    @FXML
    private ToggleGroup mailBodyOptions;

    @FXML
    private JFXRadioButton radioButtonComposeMailBody;

    @FXML
    private AnchorPane anchorSendExistingMail;

    @FXML
    private JFXTextField textFieldMailFileLocation;

    @FXML
    private AnchorPane anchorUserAuth;

    @FXML
    private JFXTextField textFieldUserName;

    @FXML
    private JFXTextField textFieldPassword;

    @FXML
    private JFXTextArea textAreaAttachments;

    @FXML
    private JFXToggleButton toggleButtonUserAuthentication;

    @FXML
    private JFXToggleButton toggleButtonAddAttachments;
    
    @FXML
    private AnchorPane anchorAddAttachments;
    
    @FXML
    private JFXTextField textFieldAttachmentFile;

    @FXML
    private JFXButton ButtonAdd;

    @FXML
    private JFXButton ButtonRemoveLast;
    
    @FXML
    private JFXTextArea textAreaConsole;

    
    RequiredFieldValidator validatorFromAddr = new RequiredFieldValidator();
    RequiredFieldValidator validatorToAddr = new RequiredFieldValidator();
    RequiredFieldValidator validatorServer = new RequiredFieldValidator();
    RequiredFieldValidator validatorSubject = new RequiredFieldValidator();
    RequiredFieldValidator validatorMailFileLocation = new RequiredFieldValidator();
    RequiredFieldValidator validatorAuthUserName = new RequiredFieldValidator();
    RequiredFieldValidator validatorAuthPassword = new RequiredFieldValidator();
    RequiredFieldValidator validatorAttachment = new RequiredFieldValidator();
    
    boolean flagSendExisting = true;
    boolean flagMailLocation = false;
    boolean flagAddAttachments = false;
    boolean flagUserAuth = false;
    boolean flagAttachments;
    
	String Dir;
	String Fs;
	String swaksPath;
	String availableAttachmentLocation;
	String osname;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Dir = System.getProperty("user.dir");
		Fs = System.getProperty("file.separator");
		osname = System.getProperty("os.name");
		swaksPath = Dir + Fs + "resources" + Fs + "bin" + Fs + "swaks.pl";
		
		textFieldFromAddress.getValidators().add(validatorFromAddr);
		validatorFromAddr.setMessage("Mandatory field. Enter Sender Address");
		
		textFieldFromAddress.focusedProperty().addListener( new ChangeListener<Boolean>() {
			public void changed(ObservableValue <? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (! newValue) {
					textFieldFromAddress.validate();
				}
			}
		});

		textFieldToAddress.getValidators().add(validatorToAddr);
		validatorToAddr.setMessage("Mandatory field. Enter Recipient(s) Address");
		
		textFieldToAddress.focusedProperty().addListener( new ChangeListener<Boolean>() {
			public void changed(ObservableValue <? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (! newValue) {
					textFieldToAddress.validate();
				}
			}
		});
		
		textFieldServer.getValidators().add(validatorServer);
		validatorServer.setMessage("Mandatory field. Enter Server name");
		
		textFieldServer.focusedProperty().addListener( new ChangeListener<Boolean>() {
			public void changed(ObservableValue <? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (! newValue) {
					textFieldServer.validate();
				}
			}
		});
		
		
		textFieldMailSubject.getValidators().add(validatorSubject);
		validatorSubject.setMessage("Mandatory field. Enter Mail Subject");
		
		textFieldMailSubject.focusedProperty().addListener( new ChangeListener<Boolean>() {
			public void changed(ObservableValue <? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (! newValue) {
					textFieldMailSubject.validate();
				}
			}
		});
	
		textFieldMailFileLocation.getValidators().add(validatorMailFileLocation);
		validatorMailFileLocation.setMessage("Plese specify a correct Mail File location");	
		
		radioButtonSendExistingMail.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) {
					anchorComposeMail.setDisable(true);
					toggleButtonAddAttachments.setDisable(true);
					toggleButtonAddAttachments.setSelected(false);
					anchorSendExistingMail.setDisable(false);
					flagSendExisting = true;
		        } else {
					anchorComposeMail.setDisable(false);
					textFieldMailFileLocation.clear();
					toggleButtonAddAttachments.setDisable(false);
					anchorSendExistingMail.setDisable(true);
					flagSendExisting = false;
		        }
		    }
		});
		
		textFieldMailFileLocation.focusedProperty().addListener( new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    	if (! newValue) {
		    		String filePathString = textFieldMailFileLocation.getText();
			    	File f = new File(filePathString);
			    	if (!(f.exists() && !f.isDirectory())) {
			    		textFieldMailFileLocation.clear();
			    		flagMailLocation = false;
			    		System.out.println("File Not present at specified Location text field");
			    	}
			    	else {
			    		flagMailLocation = true;
			    		textFieldMailFileLocation.setText(filePathString); 
			    	}
			    	textFieldMailFileLocation.validate();
		    	}
		    }
		});		

		toggleButtonAddAttachments.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) {
		        	flagAddAttachments = true;
		        	anchorAddAttachments.setDisable(false);
					textFieldAttachmentFile.clear();
					textFieldAttachmentFile.setDisable(false);
		        } else {
		        	flagAddAttachments = false;
		        	anchorAddAttachments.setDisable(true);
					textFieldAttachmentFile.clear();
					textFieldAttachmentFile.setDisable(true);
					textAreaAttachments.clear();
		        }
		    }
		});

		toggleButtonUserAuthentication.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
		        if (isNowSelected) {
		        	flagUserAuth = true;
		        	anchorUserAuth.setDisable(false);
		        	
		        } else {
		        	flagUserAuth = false;
		        	anchorUserAuth.setDisable(true);
		        }
		    }
		});

		textFieldUserName.getValidators().add(validatorAuthUserName);
		validatorAuthUserName.setMessage("Field empty. Enter User Name");
		
		textFieldUserName.focusedProperty().addListener( new ChangeListener<Boolean>() {
			public void changed(ObservableValue <? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (! newValue) {
					textFieldUserName.validate();
				}
			}
		});
		
		textFieldPassword.getValidators().add(validatorAuthPassword);
		validatorAuthPassword.setMessage("Field empty. Enter Password");
		
		textFieldUserName.focusedProperty().addListener( new ChangeListener<Boolean>() {
			public void changed(ObservableValue <? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (! newValue) {
					textFieldPassword.validate();
				}
			}
		});
		
		textFieldAttachmentFile.getValidators().add(validatorAttachment);
		validatorAttachment.setMessage("Enter Attachment File path");
		textFieldAttachmentFile.focusedProperty().addListener( new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (! newValue) {
					textFieldAttachmentFile.validate();
				}
			}
		});
	}
		
		@FXML
		public void handleAddAttachment(ActionEvent event) throws InterruptedException {
			System.out.println("Here in handleAddAttachment");
		    String textFieldAttachmentFileString = textFieldAttachmentFile.getText();
		    textFieldAttachmentFileString = textFieldAttachmentFileString.replaceAll("\\s+$","");
		    textFieldAttachmentFileString = textFieldAttachmentFileString.replaceAll("^\\s+","");
		    File f = new File(textFieldAttachmentFileString);
	    	if (f.exists() && !f.isDirectory()) {
	    		String textAreaAttachmentsString = textAreaAttachments.getText();
	    		System.out.println("Hereh in file found");
	    		if (textAreaAttachmentsString.equals("")) {
	    			textAreaAttachmentsString += "\"" + textFieldAttachmentFileString + "\"";
	    		}
	    		else {
	    		    textAreaAttachmentsString += ",\"" + textFieldAttachmentFileString + "\"";
	    		}
	    		System.out.println("Setting strin:"+textAreaAttachmentsString);
	    		textAreaAttachments.clear();
	    		textAreaAttachments.setText(textAreaAttachmentsString);
	    		textFieldAttachmentFile.clear();
	    	}
	    	else {
	    		textFieldAttachmentFile.clear();
	    		validatorAttachment.setMessage("File Not Found");
	    		textFieldAttachmentFile.validate();
	    	}
	}
		
	@FXML
	public void handleRemoveLastAttachment(ActionEvent event) throws InterruptedException {
    		String textAreaAttachmentsString = textAreaAttachments.getText();
    		if (textAreaAttachmentsString.equals("")) {
    			textAreaAttachments.clear();
    		}
    		else {
    		    String[] attachmentFiles =  textAreaAttachmentsString.split(",");
    		    int len = attachmentFiles.length;
    		    String newAttachments="";
    		    for(int i = 0; i<len-1; i++) {
    		    	if(i==len-2) {
    		    		newAttachments += attachmentFiles[i];
    		    	}
    		    	else {
    		    		newAttachments += attachmentFiles[i] + ",";
    		    	}
    		     }
    		    textAreaAttachments.clear();
    		    textAreaAttachments.setText(newAttachments);
    	    }
	}

	@FXML
	public void handleResetAttachments(ActionEvent event) throws InterruptedException {
        textAreaAttachments.clear();
    }
	
	@FXML
	public void handleSend(ActionEvent event) throws InterruptedException {

		String fromAddress = textFieldFromAddress.getText();
		String toAddress = textFieldToAddress.getText();
		String serverName = textFieldServer.getText();
		String mailSubject = textFieldMailSubject.getText();
		
		if (fromAddress.isEmpty() || toAddress.isEmpty() || serverName.isEmpty() || mailSubject.isEmpty()) {
			System.out.println("Something empty");
			return;
		}

		String cmd = " --from \"" + fromAddress +"\""
			    +" --to \"" + toAddress + "\""
			    +" --server \"" + serverName + "\""
			    +" --h-Subject \"" + mailSubject + "\"";

		if (flagSendExisting) {
			if (flagMailLocation) {
				String strMailLocation = textFieldMailFileLocation.getText();
				cmd += " --d \"" + strMailLocation + "\"";
			}
			else {
				System.out.println("Mail Location missing");
				return;
			}			
		}
		else {
			String repl = textAreaMailBody.getText();
			String strMailBody= repl.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");
			cmd += " --body \"" + strMailBody + "\"";
			if(flagAddAttachments) {
	    		String str = textAreaAttachments.getText();
	    		String reg = "(,)";
	    	    String[] files = str.split(reg);
	    	    int len = files.length;
	    	    for(int i = 0 ;i < len ; i++ ) {
	    	    	String path = files[i];
	    	    	path = path.replaceAll("^\"|\"$", "");
	    	    	File f = new File(path);
	    	    	cmd += " --attach-name \"" + f.getName()+"\""; 
	    	    	cmd += " --attach " + files[i];
	    	    }
			}		
		}
		
		if(flagUserAuth) {
			String authUser = textFieldUserName.getText();
			String authPassword = textFieldPassword.getText();
			cmd += " --auth LOGIN --auth-user \"" + authUser + "\" --auth-password \"" + authPassword + "\" -tls";
		}
		
		cmd += " --suppress-data --show-time-lapse";
	    
	    System.out.println("Swaks command: "+cmd);
	    textAreaConsole.clear();
	    buttonSend.setDisable(true);
	    Thread.sleep(2);
        try
        {            
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("perl "+ swaksPath + cmd);
            InputStream stdin = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            String consoleOutput = null;
            
            System.out.println("<OUTPUT>");
            while ( (line = br.readLine()) != null) {
                System.out.println(line);
                consoleOutput += line + "\n";
            }
            textAreaConsole.setText("Swaks Command:" + cmd + "\n" + consoleOutput);
            System.out.println("</OUTPUT>");
        
		    if(proc.exitValue() == 0) {
	            System.out.println("Command Successful");
	            Notifications notificationBuilder = Notifications.create()
	            		.title("Send Email Status")
	            		.text("Message sending completed")
	            		.graphic(null)
	            		.hideAfter(Duration.seconds(5))
	            		.position(Pos.CENTER);
	            notificationBuilder.showInformation();
	        }
	        else {
	            System.out.println("Command Failure");
	            Notifications notificationBuilder = Notifications.create()
	            		.title("SendEmail Status")
	            		.text("Message sending failed")
	            		.graphic(null)
	            		.hideAfter(Duration.seconds(5))
	            		.position(Pos.CENTER);
	            notificationBuilder.showError();
	        }
        } catch (Throwable t) {
            t.printStackTrace();
          }
        buttonSend.setDisable(false);
	}
}
