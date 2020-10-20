package Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.jupiter.api.Test;

import core.visitor.common.HashCodeNotEqual;
public class EqualHashTestCase {
	public CompilationUnit parsedunit;
	public void Compilationcreation(String path) throws FileNotFoundException, IOException{
		
		StringBuilder sb = new StringBuilder();
		String first;
	BufferedReader brr = new BufferedReader(new FileReader(path));
		while((first = brr.readLine()) != null){
			sb.append(first);
			
		}
		brr.close();
		ASTParser ast = ASTParser.newParser(AST.JLS3);
		ast.setSource(sb.toString().toCharArray());
		ast.setKind(ASTParser.K_COMPILATION_UNIT);

		parsedunit = (CompilationUnit) ast.createAST(null);
	}
	@Test
	public void testEqualnumber() throws FileNotFoundException, IOException {
		String path = Paths.get("").toAbsolutePath().toString();
		Compilationcreation(path+"/TestArtifacts/TestCases/EqualHashEqualtest.java");
		HashCodeNotEqual he =new HashCodeNotEqual (parsedunit);
		parsedunit.accept(he);
		assertFalse(he.restest);
		
		
	}
	@Test
	public void testnotEqualnumber() throws FileNotFoundException, IOException {
		String path = Paths.get("").toAbsolutePath().toString();
		Compilationcreation(path+"/TestArtifacts/TestCases/EqualHashNotEqualtest.java");
		HashCodeNotEqual he =new HashCodeNotEqual (parsedunit);
		parsedunit.accept(he);
		
	assertTrue(he.restest);
		
		
	}

}
