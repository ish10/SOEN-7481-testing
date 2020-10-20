package core.visitor.common;
import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.IPackageFragment;


import core.datastructure.impl.Method;

import core.helper.HelperClass;
import core.helper.ObjectCreationHelper;
public class UselessControlFlow extends ASTVisitor {
	private String fileName = "UselessControlFlow.txt"; //Name of the output file that you wish to have
	private String sysName = "Kafka-2.3.0"; //Name of the project
	private String className = "";
	private CompilationUnit parsedunit;
    public boolean ifresult =false;
    public boolean elseresult =false;
    public boolean whileresult =false;
    public boolean forresult =false;
	public  UselessControlFlow(IPackageFragment packageFrag, ICompilationUnit unit, CompilationUnit parsedunit) {

		className = unit.getElementName().split("\\.")[0];
		this.parsedunit = parsedunit;
		System.out.println(className);
	}
	public UselessControlFlow(CompilationUnit parsedunit)
	{
		this.parsedunit = parsedunit;
	}
	

	public boolean visit(MethodDeclaration method) {

		final Method callsite = ObjectCreationHelper.createMethodFromMethodDeclaration(method, className);
		
		if (method.getBody() != null) {
			method.getBody().accept(new ASTVisitor() {
				@Override
				//logic for if statement 
				public boolean visit(IfStatement ifbody) {
     Statement result=ifbody.getThenStatement(); 
     String catchBody = result.toString().replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
     if(catchBody.trim().length() == 0)
     
					{
    	 int lineNumber = parsedunit.getLineNumber(ifbody.getStartPosition());
					
           ifresult=true;
						//System.out.println("Empty Catch Block:" + callsite + " :" + lineNumber);
						String str = "<system>" + sysName + "</system>" + "<callsite>" + className
								+ "</callsite>" + "<line>" + lineNumber + "</line>";
						try {
							HelperClass.fileAppendMethod(fileName, str);
						} catch (IOException e) {
							e.printStackTrace();
			
						}
						

					}
 	
     
     Statement result1=ifbody.getElseStatement(); 
   //logic for else statement 
     if(result1 != null)
     {
    	 String catchBody1 = result1.toString().replaceAll("\\{", "").replaceAll("\\}", "")
 				.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");	 
    	 if(catchBody1.trim().length() == 0) {
    		 
    		 
    		 elseresult=true;
				int lineNumber = parsedunit.getLineNumber(ifbody.getStartPosition());
				

				
					String str = "<system>" + sysName + "</system>" + "<callsite>" + className
							+ "</callsite>" + "<line>" + lineNumber + "</line>";
					try {
						HelperClass.fileAppendMethod(fileName, str);
					} catch (IOException e) {
						e.printStackTrace();
		
					}
					
    		 
    	 }
     }
					return true;
				}
				@Override
				//logic for while statement 
				public boolean visit(WhileStatement whilebody) {
     Statement result=whilebody.getBody(); 
     String catchBody = result.toString().replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
     if(catchBody.trim().length() == 0) {
    	 
					int lineNumber = parsedunit.getLineNumber(whilebody.getStartPosition());
					
         whileresult=true;
				
						String str = "<system>" + sysName + "</system>" + "<callsite>" + className
								+ "</callsite>" + "<line>" + lineNumber + "</line>";
						try {
							HelperClass.fileAppendMethod(fileName, str);
						} catch (IOException e) {
							e.printStackTrace();
						}
						

					}

					return true;
				}
				
				//logic for do statement 
				@Override
				public boolean visit(DoStatement dobody) {
     Statement result=dobody.getBody(); 
     String catchBody = result.toString().replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
     if(catchBody.trim().length() == 0) {
    	 
					int lineNumber = parsedunit.getLineNumber(dobody.getStartPosition());
					
         whileresult=true;
						
						String str = "<system>" + sysName + "</system>" + "<callsite>" + className
								+ "</callsite>" + "<line>" + lineNumber + "</line>";
						try {
							HelperClass.fileAppendMethod(fileName, str);
						} catch (IOException e) {
							e.printStackTrace();
						}
						

					}

					return true;
				}
				//logic for for statement 
				@Override
				public boolean visit(ForStatement forbody) {
     Statement result=forbody.getBody(); 
     String catchBody = result.toString().replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
     if(catchBody.trim().length() == 0)
     {
    	 
					int lineNumber = parsedunit.getLineNumber(forbody.getStartPosition());
					forresult=true;

						
						String str = "<system>" + sysName + "</system>" + "<callsite>" + className
								+ "</callsite>" + "<line>" + lineNumber + "</line>";
						try {
							HelperClass.fileAppendMethod(fileName, str);
						} catch (IOException e) {
							e.printStackTrace();
						}
						

					}

					return true;
				}
			});

		}

		return true;
	}


}
