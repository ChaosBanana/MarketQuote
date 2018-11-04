package zopa;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import zopa.exception.CsvDataParseError;

/**
 * Generic CSV file to bean importer.
 * <br>
 * Use {@code beanList} to improve speed or
 * {@code beanIterator} to save memory when reading large files.
 * 
 * @param <T> Type of beans to import
 */
public class CsvImport<T> {

	/**
	 * Loads all CSV data to a List.
	 * <br>
	 * Consumes memory linearly with CSV file size.
	 * Improves reading speed and makes multithreading possible.
	 * @param path Location of the CSV file
	 * @param type Class of the bean
	 * @return List of beans imported from the CSV file
	 * @throws IOException
	 * @throws CsvDataParseError 
	 */
	public static <T> List<T> beanList(String path, Class<T> type) throws IOException, CsvDataParseError {
		try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
			CsvToBean<T> csvToBean = build(reader, type);
			try {
				return csvToBean.parse();				
			} catch (RuntimeException e) {
				throw new CsvDataParseError(e);
			}
		}
	}

	/**
	 * Provides an Iterator that reads data from {@code reader}.
	 * 
	 * @param reader Reader to read data from
	 * @param type Class of the bean
	 * @return Iterator that reads beans from {@code reader}
	 * @throws IOException
	 */
	public static <T> Iterator<T> beanIterator(Reader reader, Class<T> type) throws IOException {
		CsvToBean<T> csvToBean = build(reader, type);
		Iterator<T> iterator = csvToBean.iterator();
		return iterator;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> CsvToBean<T> build(Reader reader, Class<T> type) throws IOException {
		CsvToBeanBuilder<T> builder = new CsvToBeanBuilder(reader).withType(type);
		builder.withIgnoreLeadingWhiteSpace(true);
		builder.withOrderedResults(false);
		
		CsvToBean<T> csvToBean = builder.build();
		return csvToBean;
	}
	
	

}
