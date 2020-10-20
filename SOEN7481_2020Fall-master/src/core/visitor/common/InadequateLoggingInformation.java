package core.visitor.common;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.IPackageFragment;


import core.datastructure.impl.Method;

import core.helper.HelperClass;
import core.helper.ObjectCreationHelper;
public class InadequateLoggingInformation extends ASTVisitor {
	private String fileName = "InadequateLogging.txt"; //Name of the output file that you wish to have
	private String sysName = "Kafka-2.3.0"; //Name of the project
	private String className = "";
	private CompilationUnit parsedunit;
    public boolean logging=false;
	public InadequateLoggingInformation(IPackageFragment packageFrag, ICompilationUnit unit, CompilationUnit parsedunit) {

		className = unit.getElementName().split("\\.")[0];
		this.parsedunit = parsedunit;

	}
	public InadequateLoggingInformation(CompilationUnit parsedunit)
	{
		this.parsedunit = parsedunit;
	}

	public boolean visit(MethodDeclaration method) {

		final Method callsite = ObjectCreationHelper.createMethodFromMethodDeclaration(method, className);

		if (method.getBody() != null) {
			method.getBody().accept(new ASTVisitor() {
				@Override
				public boolean visit(TryStatement tryst) {
					int lineNumber=0;
					
					List<CatchClause> catchcl = tryst.catchClauses();
					HashMap<String, Integer> unique = new HashMap<>();
					
					 //iterating catch statements in particular try block 
					
					 for(int j=0;j<catchcl.size();j++){
						Statement body= catchcl.get(j).getBody();
					
						String catchBody =body.toString().replaceAll("\\{", "").replaceAll("\\}", "")
								.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
						
						 //if catch block have multiple messages seprating them and storing them in array
						 //then iterating the array
						
						String spl[]= catchBody.split(";");
						for(int i=0;i<spl.length;i++) {
							
							int first = spl[i].indexOf("(");
							int last = spl[i].lastIndexOf(")");
							
							 //Abstacting the message in ()round brackets and checking if it is a message 
							 //if message is from same catch block then bug is not reported.
							if(first != -1 && last!=-1 ) {
							String catchBody1 = spl[i].substring(first+1, last);
							int first1 = catchBody1.indexOf("\"");
							int last1 = catchBody1.lastIndexOf("\"");
							int size= catchBody1.length();
							if(first1 ==0 && last1==size-1) {
							String catchBody2 = catchBody1.replaceAll("\"","");  
							
							 lineNumber = parsedunit.getLineNumber(catchcl.get(j).getStartPosition());
							
							if(unique.containsKey(catchBody2)  == false) {
							unique.put(catchBody2 , j);}
							
							else {
								
								 //if message is from same catch block then bug is not reported.
							
								if(unique.get(catchBody2)==j) {
									continue;
								}
								else {
								logging=true;
								
								String str = "<system>" + sysName + "</system>" + "<callsite>" + className
										+ "</callsite>" + "<line>" + lineNumber + "</line>";
								try {
									HelperClass.fileAppendMethod(fileName, str);
								} catch (IOException e) {
									e.printStackTrace();
								}}
							}
							}
							}
						}
						
					}
				

					return true;
				}

			});

		}

		return true;
	}
}

