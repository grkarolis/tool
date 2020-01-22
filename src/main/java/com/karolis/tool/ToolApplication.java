package com.karolis.tool;

import java.io.IOException;
import java.util.Scanner;

public class ToolApplication {

	public static void main(String[] args) throws IOException {
		ExcelWorker worker = new ExcelWorker();
		Scanner input = new Scanner(System.in);

		System.out.println("Please choose what you want to do:");
		System.out.println("1. Find duplicates.");
		System.out.println("2. Remove duplicates.");

		switch(input.nextInt()) {
			case 1:
				System.out.println("Enter file names:");
				String name = input.next();
				String name1 = input.next();

				worker.findDuplicates(
						addExtension(name, "xlsx"),
						addExtension(name1, "xlsx")
				);
				System.out.println("Done");
				break;

			case 2:
				System.out.println("Enter file names:");
				String name2 = input.next();
				String name3 = input.next();

				worker.removeDuplicates(
						addExtension(name2, ".xlsx"),
						addExtension(name3, ".xlsx")
				);
				System.out.println("Done");

			default:
				break;
		}
	}

	public static String addExtension(String file, String extension) {
		return String.format("%s.%s", file, extension);
	}

}
