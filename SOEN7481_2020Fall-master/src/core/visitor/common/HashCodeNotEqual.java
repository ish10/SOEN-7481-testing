package core.visitor.common;

import java.io.IOException;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.IPackageFragment;


import core.datastructure.impl.Method;

import core.helper.HelperClass;
import core.helper.ObjectCreationHelper;

public class HashCodeNotEqual extends ASTVisitor {

	private String fileName = "HascodeNotEqual.txt"; //Name of the output file that you wish to have
	private String sysName = "Kafka-2.3.0"; //Name of the project
	private String className = "";
	private CompilationUnit parsedunit;
	public boolean restest=false;

	public HashCodeNotEqual(IPackageFragment packageFrag, ICompilationUnit unit, CompilationUnit parsedunit) {

		className = unit.getElementName().split("\\.")[0];
		this.parsedunit = parsedunit;

	}
	public HashCodeNotEqual(CompilationUnit parsedunit)
	{
		this.parsedunit = parsedunit;
	}
	
	public boolean visit(TypeDeclaration allmethod) {

		MethodDeclaration[] methodnodes=allmethod.getMethods();
		
		int lineNumber=0;
		int lineNumber1=0;
		int equalscont=0;
		int hashcodecont=0;
	  
	for(int i=0;i<methodnodes.length;i++)
	{
		MethodDeclaration methodnodes1=methodnodes[i];
		if(methodnodes1.getName().toString().equals("hashCode")) {
			hashcodecont++;
			lineNumber= parsedunit.getLineNumber(methodnodes1.getStartPosition());
		}
		 String name =methodnodes1.getName().toString().trim().toLowerCase();
			
		if(name.equals("equals"))
		{ 
			lineNumber1= parsedunit.getLineNumber(methodnodes1.getStartPosition());
			equalscont++;	
		}
			
		
		
		
	}
	if (hashcodecont !=0   && hashcodecont !=equalscont) {
		restest=true;
		if(lineNumber == 0) {
			String str = "<system>" + sysName + "</system>" + "<callsite>" + className
					+ "</callsite>" + "<line>" + lineNumber1 + "</line>";
			try {
				HelperClass.fileAppendMethod(fileName, str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
		//System.out.println("Empty Catch Block:" + callsite + " :" + lineNumber);
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

}

