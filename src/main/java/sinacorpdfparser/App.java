/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package sinacorpdfparser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.io.PrintWriter;

import freemarker.template.TemplateException;

public class App {

	public static void main(String[] args)
			throws IOException, TemplateException, IllegalArgumentException, IllegalAccessException, ParseException {

		ClienteBuilder clienteBuilder = new ClienteBuilder();

		Options options = new Options();

		options.addOption(Option.builder()
				.longOpt("arquivo")
				.argName("localizacao do arquivo .pdf")
				.hasArg()
				.desc("caminho do arquivo .pdf")
				.build());

		options.addOption(Option.builder()
				.longOpt("senha")
				.argName("senha do arquivo")
				.hasArg()
				.desc("senha do arquivo, quando houver")
				.build());

		options.addOption("bovespa", false, "notas de corretagem de operacoes na bovespa").addOption("bmf", false,
				"notas de corretagem de operacoes na bmf");

		CommandLineParser cmdLineParser = new DefaultParser();

		CommandLine cmd = cmdLineParser.parse(options, args);

		HelpFormatter formatter = new HelpFormatter();

		final PrintWriter writer = new PrintWriter(System.out);

		if (!cmd.hasOption("arquivo")) {

			formatter.printUsage(writer, 80, "java -jar build/libs/SinacorPDFParser-all.jar", options);
			writer.flush();

		} else {
			clienteBuilder.setCaminho(cmd.getOptionValue("arquivo"));
			if (cmd.hasOption("senha")) {
				clienteBuilder.setSenha(cmd.getOptionValue("senha"));
			}
		}
		
		if (cmd.hasOption("bovespa")) {
			clienteBuilder.setParser(new ParserBovespa());
			System.out.println((clienteBuilder.build()).executar());
		} else if (cmd.hasOption("bmf")) {
			clienteBuilder.setParser(new ParserBMF());
			System.out.println((clienteBuilder.build()).executar());
		} else {
			clienteBuilder.setParser(new ParserBovespa());
			System.out.println((clienteBuilder.build()).executar());
			clienteBuilder.setParser(new ParserBMF());
			System.out.println((clienteBuilder.build()).executar());
		}
		
	}
}
