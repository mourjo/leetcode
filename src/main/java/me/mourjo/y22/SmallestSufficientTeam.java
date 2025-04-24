package me.mourjo.y22;

/*
https://leetcode.com/problems/smallest-sufficient-team/
In a project, you have a list of required skills req_skills, and a list of people.
The i-th person people[i] contains a list of skills that person has.

Consider a sufficient team: a set of people such that for every required skill in req_skills,
 there is at least one person in the team who has that skill.  We can represent these teams
 by the index of each person: for example,
 team = [0, 1, 3] represents the people with skills people[0], people[1], and people[3].


Return any sufficient team of the smallest possible size, represented by the index
of each person.

You may return the answer in any order.  It is guaranteed an answer exists.



Example 1:
Input: req_skills = ["java","nodejs","reactjs"],
people =            [["java"],["nodejs"],["nodejs","reactjs"]]
Output:             [0,2]

Example 2:
Input: req_skills = ["algorithms","math","java","reactjs","csharp","aws"],
people =           [["algorithms","math","java"],
                    ["algorithms","math","reactjs"],
                    ["java","csharp","aws"],
                    ["reactjs","csharp"],
                    ["csharp","math"],
                    ["aws","java"]]
Output:             [1,2]
 */


import java.util.*;

public class SmallestSufficientTeam {

    static int counter = 0;

    public static int[] smallestSufficientTeam(String[] r, List<List<String>> rawSkills) {

        HashMap<String, Integer> skillToID = new HashMap<>(r.length);
        for (int i = 0; i < r.length; i++) {
            skillToID.put(r[i], i);
        }

        int allSkillsBitMap = (1 << skillToID.size()) - 1;
        int noSkillBitMap = 0;

        // each row in the table is a bit mask of the skills the people in it provide
        // +1 because I need idx 0 to allSkillsBitMap
        HashSet<Integer>[] table = new HashSet[allSkillsBitMap + 1];

        table[noSkillBitMap] = new HashSet<>();

        for (int me = 0; me < rawSkills.size(); me++) {
            int mySkillSet = noSkillBitMap;
            for (String mySkill : rawSkills.get(me)) {
                if (skillToID.containsKey(mySkill)) {
                    mySkillSet |= (1 << skillToID.get(mySkill));
                }
            }

            table[mySkillSet] = new HashSet<>();
            table[mySkillSet].add(me);

            if (mySkillSet == allSkillsBitMap) {
                break;
            }

            for (int skillCombination = 0; skillCombination < table.length; skillCombination++) {
                if (table[skillCombination] == null) {
                    continue;
                }
                if (skillCombination == (skillCombination | mySkillSet)) {
                    continue;
                }

                int newCombination = skillCombination | mySkillSet;

                if (table[newCombination] == null ||
                        table[skillCombination].size() + 1 < table[newCombination].size()) {
                    table[newCombination] = new HashSet<>(table[skillCombination]);
                    table[newCombination].add(me);
                }
            }
        }

        Set<Integer> peopleWithAllSkills = table[allSkillsBitMap];
        int[] result = new int[peopleWithAllSkills.size()];
        int c = 0;
        for (int person : peopleWithAllSkills) {
            result[c++] = person;
        }

        return result;
    }

    public static void main(String[] args) {

        check("0,2",
                smallestSufficientTeam(
                        new String[]{"java", "nodejs", "reactjs"},
                        people(new String[][]{{"java"}, {"nodejs"}, {"nodejs", "reactjs", "notfound"}})));

        check("1,2",
                smallestSufficientTeam(new String[]
                                {"algorithms", "math", "java", "reactjs", "csharp", "aws"},
                        people(new String[][]{
                                {"algorithms", "math", "java"},
                                {"algorithms", "math", "reactjs"},
                                {"java", "csharp", "aws"},
                                {"reactjs", "csharp"},
                                {"csharp", "math"},
                                {"aws", "java"}})));

        check("5,6,9,15",
                smallestSufficientTeam(
                        new String[]{"gzkytqcynt", "kcoobskzamd", "ddofnsczakzrocob", "zjqdvz", "mksriiu",
                                "aeuauwedm", "u", "y"},
                        people(new String[][]{
                                        {"ddofnsczakzrocob", "zjqdvz", "mksriiu"},
                                        {"zjqdvz"},
                                        {},
                                        {"kcoobskzamd", "ddofnsczakzrocob", "mksriiu", "notfound"},
                                        {"mksriiu"},
                                        {"y"},
                                        {"kcoobskzamd", "zjqdvz"},
                                        {},
                                        {"zjqdvz", "mksriiu"},
                                        {"gzkytqcynt", "u"},
                                        {},
                                        {},
                                        {"u"},
                                        {},
                                        {"mksriiu"},
                                        {"ddofnsczakzrocob", "mksriiu", "aeuauwedm"},
                                        {"gzkytqcynt", "zjqdvz", "y"},
                                        {},
                                        {"zjqdvz"},
                                        {},
                                        {"mksriiu", "u"},
                                        {},
                                        {},
                                        {},
                                        {"gzkytqcynt"},
                                        {"ddofnsczakzrocob", "zjqdvz"},
                                        {"gzkytqcynt", "ddofnsczakzrocob"},
                                        {},
                                        {},
                                        {"zjqdvz", "u"},
                                        {"mksriiu", "aeuauwedm"},
                                        {"zjqdvz"},
                                        {},
                                        {},
                                        {"gzkytqcynt", "ddofnsczakzrocob", "zjqdvz", "mksriiu"},
                                        {"gzkytqcynt", "ddofnsczakzrocob", "zjqdvz", "mksriiu"},
                                        {"ddofnsczakzrocob", "zjqdvz"},
                                        {"u"},
                                        {"gzkytqcynt"},
                                        {"ddofnsczakzrocob", "mksriiu", "u"},
                                        {"y"},
                                        {"mksriiu"},
                                        {"ddofnsczakzrocob", "zjqdvz", "y"},
                                        {"ddofnsczakzrocob"},
                                        {"mksriiu"},
                                        {"u"},
                                        {"mksriiu"},
                                        {"mksriiu"},
                                        {"ddofnsczakzrocob"},
                                        {"ddofnsczakzrocob", "mksriiu", "y"}
                                }
                        )));

        check("46",
                smallestSufficientTeam(
                        new String[]{"gzkytqcynt", "kcoobskzamd", "ddofnsczakzrocob", "zjqdvz", "mksriiu",
                                "aeuauwedm", "u", "y"},
                        people(new String[][]{
                                        {"ddofnsczakzrocob", "zjqdvz", "mksriiu"},
                                        {"zjqdvz"},
                                        {},
                                        {"kcoobskzamd", "ddofnsczakzrocob", "mksriiu", "notfound"},
                                        {"mksriiu"},
                                        {"y"},
                                        {"kcoobskzamd", "zjqdvz"},
                                        {},
                                        {"zjqdvz", "mksriiu"},
                                        {"gzkytqcynt", "u"},
                                        {},
                                        {},
                                        {"u"},
                                        {},
                                        {"mksriiu"},
                                        {"ddofnsczakzrocob", "mksriiu", "aeuauwedm"},
                                        {"gzkytqcynt", "zjqdvz", "y"},
                                        {},
                                        {"zjqdvz"},
                                        {},
                                        {"mksriiu", "u"},
                                        {},
                                        {},
                                        {},
                                        {"gzkytqcynt"},
                                        {"ddofnsczakzrocob", "zjqdvz"},
                                        {"gzkytqcynt", "ddofnsczakzrocob"},
                                        {},
                                        {},
                                        {"zjqdvz", "u"},
                                        {"mksriiu", "aeuauwedm"},
                                        {"zjqdvz"},
                                        {},
                                        {},
                                        {"gzkytqcynt", "ddofnsczakzrocob", "zjqdvz", "mksriiu"},
                                        {"gzkytqcynt", "ddofnsczakzrocob", "zjqdvz", "mksriiu"},
                                        {"ddofnsczakzrocob", "zjqdvz"},
                                        {"u"},
                                        {"gzkytqcynt"},
                                        {"ddofnsczakzrocob", "mksriiu", "u"},
                                        {"y"},
                                        {"mksriiu"},
                                        {"ddofnsczakzrocob", "zjqdvz", "y"},
                                        {"ddofnsczakzrocob"},
                                        {"mksriiu"},
                                        {"u"},
                                        {"gzkytqcynt", "kcoobskzamd", "ddofnsczakzrocob", "zjqdvz", "mksriiu",
                                                "aeuauwedm",
                                                "u", "y"},
                                        {"mksriiu"},
                                        {"mksriiu"},
                                        {"ddofnsczakzrocob"},
                                        {"ddofnsczakzrocob", "mksriiu", "y"}
                                }
                        )));
    }

    static void check(String exp, int[] x) {
        List<Integer> t = new ArrayList<>();
        System.out.println(">Counter: " + counter++);
        System.out.println("Expected: " + exp);
        for (int a : x) {
            t.add(a);
        }

        Collections.sort(t);
        System.out.println("Actual: " + t);
        System.out.println();
    }

    public static List<List<String>> people(String[][] p) {
        List<List<String>> x = new ArrayList<>();
        for (String[] personSkills : p) {
            List<String> skills = new ArrayList<>();
            Collections.addAll(skills, personSkills);
            x.add(skills);
        }
        return x;
    }

}
