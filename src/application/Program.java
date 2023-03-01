package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		System.out.println();
		
		List<Sale> list = new ArrayList<>();
		sc.close();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String name = fields[2];
				int items = Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				list.add(new Sale(month, year, name, items, total));
				line = br.readLine();
				
			}
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			
			List<Sale> avg2016 = list.stream()
					.filter(y -> y.getYear() == 2016)
					.sorted((x, y) -> y.averagePrice().compareTo(x.averagePrice()))
					.limit(5)
					.collect(Collectors.toList());
			
			for (Sale s : avg2016) {
				System.out.println(s);
			}
			System.out.println();
			
			double totalLogan1e7 = list.stream()
					.filter(s -> s.getSeller().equals("Logan"))
					.filter(s -> s.getMonth() == 1 || s.getMonth() == 7)
					.map(s -> s.getTotal())
					.reduce(0.0, (a, b) -> a + b);
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f", totalLogan1e7);
			

		}catch (IOException e) {
			System.out.printf("Erro %s (O sistema não pode encontrar o arquivo especificado)", path);
		} 		
		
		
	}

}
