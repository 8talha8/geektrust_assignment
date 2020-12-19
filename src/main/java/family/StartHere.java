package family;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import family.model.Person;

public class StartHere {
	static ArrayList<Person> familyList = new ArrayList<Person>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		contruct();
		Scanner input = null;
		try {
			input = new Scanner(new File(args[0]));
			while (input.hasNextLine()) {
				String s = input.nextLine();
				String[] part = s.split(" ");
				if ("ADD_CHILD".equals(part[0])) {
					addChild(part[1], part[2], part[3]);
				} else if ("GET_RELATIONSHIP".equals(part[0])) {
					getRelationship(part[1], part[2]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			input.close();
		}

	}

	private static void getRelationship(String name, String relation) {
		// TODO Auto-generated method stub
		Optional<Person> thisPerson = familyList.stream().filter(p -> name.equals(p.getName())).findFirst();

		if (thisPerson.isPresent()) {
			switch (relation) {
			case "Paternal-Uncle":
				maternalPaternal(thisPerson, true, false);
				break;

			case "Maternal-Uncle":
				maternalPaternal(thisPerson, true, true);
				break;
			case "Paternal-Aunt":
				maternalPaternal(thisPerson, false, false);
				break;
			case "Maternal-Aunt ":
				maternalPaternal(thisPerson, false, true);
				break;
			case "Sister-In-Law":
				sisterInLaw(thisPerson);
				break;
			case "Brother-In-Law":
				brotherInLaw(thisPerson);
				break;
			case "Son":
				son(name);
				break;
			case "Daughter":
				daugther(name);
				break;
			case "Siblings":
				sibling(thisPerson);
				break;
			default:
			}
		} else {
			System.out.println("PERSON_NOT_FOUND");
		}

	}

	private static void sibling(Optional<Person> thisPerson) {
		List<String> s = familyList.stream()
				.filter(p -> thisPerson.get().getParent().equals(p.getParent()) && p.isMale()
						&& !"NA".equals(thisPerson.get().getParent()))
				.map(e -> e.getName()).collect(Collectors.toList());
		print(s);
	}

	private static void daugther(String name) {
		List<String> s = familyList.stream().filter(p -> name.equals(p.getParent()) && !p.isMale())
				.map(e -> e.getName()).collect(Collectors.toList());
		print(s);
	}

	private static void print(List<String> s) {
		if (s.isEmpty()) {
			System.out.println("NONE");
		} else {
			s.forEach(p -> {
				System.out.print(p + " ");
			});
			System.out.println();
		}
	}

	private static void son(String name) {
		List<String> s = familyList.stream().filter(p -> name.equals(p.getParent()) && p.isMale()).map(e -> e.getName())
				.collect(Collectors.toList());
		print(s);
	}

	private static void maternalPaternal(Optional<Person> thisPerson, boolean male, boolean maternal) {
		if ("NA".equals(thisPerson.get().getParent())) {

			System.out.println("PERSON_NOT_FOUND");
		} else {
			String grandMother;
			if (maternal) {
				grandMother = familyList.stream().filter(p -> thisPerson.get().getParent().equals(p.getName()))
						.findFirst().get().getParent();
			} else {

				String mothersPartner = familyList.stream()
						.filter(p -> thisPerson.get().getParent().equals(p.getName())).findFirst().get().getPartner();
				grandMother = familyList.stream().filter(p -> mothersPartner.equals(p.getName())).findFirst().get()
						.getParent();

			}
			if ("NA".equals(grandMother)) {

				System.out.println("PERSON_NOT_FOUND");
			} else {
				if (male) {
					List<String> s = familyList.stream()
							.filter(p -> grandMother.equals(p.getParent()) && p.isMale()
									&& !p.getPartner().equals(thisPerson.get().getParent()))
							.map(e -> e.getName()).collect(Collectors.toList());
					print(s);
				} else {
					List<String> s = familyList.stream()
							.filter(p -> grandMother.equals(p.getParent()) && !p.isMale()
									&& !p.getName().equals(thisPerson.get().getParent()))
							.map(e -> e.getName()).collect(Collectors.toList());
					print(s);
				}
			}
		}
	}

	private static void brotherInLaw(Optional<Person> thisPerson) {
		if ("NA".equals(thisPerson.get().getParent())) {
			Optional<Person> partner = familyList.stream()
					.filter(p -> thisPerson.get().getPartner().equals(p.getName())).findFirst();
			if (partner.isPresent()) {
				List<String> s = familyList.stream()
						.filter(p -> partner.get().getParent().equals(p.getParent()) && p.isMale()
								&& !partner.get().getName().equals(p.getName()))
						.map(e -> e.getName()).collect(Collectors.toList());
				print(s);
			} else
				System.out.println("NONE");
		} else {
			List<String> s = familyList.stream()
					.filter(p -> thisPerson.get().getParent().equals(p.getParent()) && !p.isMale()
							&& "NA".equals(thisPerson.get().getPartner()))
					.map(e -> e.getPartner()).collect(Collectors.toList());
			print(s);
		}
	}

	private static void sisterInLaw(Optional<Person> thisPerson) {
		if ("NA".equals(thisPerson.get().getParent())) {
			Optional<Person> partner = familyList.stream()
					.filter(p -> thisPerson.get().getPartner().equals(p.getName())).findFirst();
			if (partner.isPresent()) {
				List<String> s = familyList.stream()
						.filter(p -> partner.get().getParent().equals(p.getParent()) && !p.isMale())
						.map(e -> e.getName()).collect(Collectors.toList());
				print(s);
			} else
				System.out.println("NONE");
		} else {
			List<String> s = familyList.stream()
					.filter(p -> thisPerson.get().getParent().equals(p.getParent()) && p.isMale()
							&& "NA".equals(thisPerson.get().getPartner()))
					.map(e -> e.getPartner()).collect(Collectors.toList());
			print(s);
		}
	}

	private static void addChild(String name, String child, String gender) {
		// TODO Auto-generated method stub
		Optional<Person> parent = familyList.stream().filter(p -> name.equals(p.getName())).findFirst();
		if (parent.isPresent()) {
			if (!parent.get().isMale()) {
				Person p = new Person(child, name, "Male".equalsIgnoreCase(gender), "NA");
				familyList.add(p);
				System.out.println("CHILD_ADDITION_SUCCEEDED");
			} else {
				System.out.println("CHILD_ADDITION_FAILED");
			}
		} else {
			System.out.println("PERSON_NOT_FOUND");
		}

	}

	private static void contruct() {
		// TODO Auto-generated method stub
		String[][] data = { { "Shan", "NA", "true", "Anga" }, { "Anga", "NA", "false", "Shan" },
				{ "Chit", "Anga", "true", "Amba" }, { "Amba", "NA", "false", "Chit" }, { "Ish", "Anga", "true", "NA" },
				{ "Vich", "Anga", "true", "Lika" }, { "Lika", "NA", "false", "Vich" },
				{ "Aras", "Anga", "true", "Chitra" }, { "Chitra", "NA", "false", "Aras" },
				{ "Satya", "Anga", "false", "Vyan" }, { "Vyan", "NA", "true", "Satya" },
				{ "Dritha", "Amba", "false", "Jaya" }, { "Jaya", "NA", "true", "Dritha" },
				{ "Tritha", "Amba", "false", "NA" }, { "Vritha", "NA", "true", "NA" },
				{ "Vila", "Lika", "false", "NA" }, { "Chika", "Lika", "true", "NA" }, { "Arit", "NA", "true", "Jnki" },
				{ "Jnki", "Chitra", "false", "Arit" }, { "Ahit", "Chitra", "true", "NA" },
				{ "Satvy", "NA", "false", "Asva" }, { "Asva", "Satya", "true", "Satvy" },
				{ "Krpi", "NA", "true", "Vyas" }, { "Vyas", "Satya", "true", "Krpi" },
				{ "Atya", "Satya", "false", "Anga" }, { "Yodhan", "Dritha", "true", "NA" },
				{ "Laki", "Jnki", "true", "NA" }, { "Lanvya", "Jnki", "false", "NA" },
				{ "Vasa", "Satvy", "true", "NA" }, { "Kriya", "Krpi", "true", "NA" },
				{ "Krithi", "Krpi", "false", "NA" }, };
		for (int i = 0; i < data.length; i++) {
			Person p = new Person(data[i][0], data[i][1], ("true".equals(data[i][2]) ? true : false), data[i][3]);
			familyList.add(p);
		}

	}

}
