import java.util.*;

//this is very long bc it contains an entry for every pokemon and an entry for every paldea mon
public class Database {
    private static final HashMap<String, Pokemon> natDex = new HashMap<>();
    private static final HashMap<String, Move> moveList = new HashMap<>();
    private static final ArrayList<String> itemList = new ArrayList<>();
    private static final ArrayList<String> abilityList = new ArrayList<>();
    private static final HashMap<String, Nature> natureList = new HashMap<>();
    private static final Map<String, Type> typeList = new HashMap<>(); //allows u to sort by type name
    private static final Map<String,Pokemon> viableList = new HashMap<>();

    //ughhh repeated code.. u have to do the same thing for every method here cuz the parameters dont match up otherwise-----
    public static String[] getNatDexList(){return HelperMethods.getMapAsList(natDex.keySet());}

    public static String[] getMoveList(){return HelperMethods.getMapAsList(moveList.keySet());}

    public static String[] getNatureList(){return HelperMethods.getMapAsList(natureList.keySet());}

    public static String[] getItemList(){return HelperMethods.arrayListToArray(itemList);}

    public static String[] getAbilityList(){return HelperMethods.arrayListToArray(abilityList);}


    public static Nature getNature(String name){return natureList.get(name);}

    public static Move getMove(String input){return moveList.get(input);}

    public static Pokemon[] getViablePokemonList(){
        final ArrayList<Pokemon> arrayList = new ArrayList<>(viableList.values());
        final int size = arrayList.size();
        final Pokemon[] array = new Pokemon[size];

        for (int i = 0; i < size; i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }

    public static Pokemon getPokemon(String input){
        Pokemon result=natDex.get(input);


        if(result==null) {
            ErrorPrinter.setDetails(input, false);
            ErrorPrinter.handler(ErrorPrinter.ERROR_CODE.ABN_DB_MISSINGNO, null);
            return natDex.get("Gallade");
        }

        boolean secondTypeMalformed = result.types.length>1&&result.types[1]==null;
        if(result.types[0]==null||secondTypeMalformed){
            ErrorPrinter.setDetails(input, false);
            ErrorPrinter.handler(ErrorPrinter.ERROR_CODE.ABN_DB_BIRDTYPE, null);
            return natDex.get("Gallade");
        }

        return result;
    }

    //this could technically only be one line but then it would be incredibly unreadable
    public static Type getType(String input){
        Type output = typeList.get(input);

        if(output==null){
            ErrorPrinter.setDetails(input,false);
            ErrorPrinter.handler(ErrorPrinter.ERROR_CODE.ABN_DB_TYPE_DNE, null);
            return typeList.get("Normal");
        }else{return output;}
    }


    public static double getMatchups(Type[] types, String attackType) {
        final Type typeOne = types[0];
        double typeTwoMod = 1;
        if(types.length!=1){typeTwoMod = Type.returnOneMatchup(types[1], attackType);}

        return (Type.returnOneMatchup(typeOne, attackType)*typeTwoMod);
    }

    //THANK U SO MUCH FOR DOING ALL THE TEDIOUS ASS FORMATTING LEXI!!! :HEART EMOJI:
    public static void initialize(){
        ErrorPrinter.init();
        Type.init();
        Items.init();
        Move.init();
        Nature.init();
        Pokemon.init();
        Pokemon.initViable();
    }

    static public class Pokemon {
        public int dexNumber;
        public String name;
        public Type[] types;
        public int baseHP;
        public int baseAttack;
        public int baseDefense;
        public int baseSpatk;
        public int baseSpdef;
        public int baseSpeed;
        public int[] stats;

        private Pokemon(int dexNumber, String name, Type[] types, int HP, int attack, int defense, int spatk, int spdef, int speed) {
            this.dexNumber = dexNumber;
            this.name = name;
            this.types = types;
            baseHP = HP;
            baseAttack = attack;
            baseDefense = defense;
            baseSpatk = spatk;
            baseSpdef = spdef;
            baseSpeed = speed;
            stats = new int[]{HP,attack,defense,spatk,spdef,speed};


            natDex.put(name, this);
        }

        private static void init(){
            new Pokemon(1, "Bulbasaur", new Type[]{typeList.get("Grass"), typeList.get("Poison")},45,49,49,65,65,45);
            new Pokemon(2, "Ivysaur", new Type[]{typeList.get("Grass"),typeList.get("Poison")},60,62,63,80,80,60);
            new Pokemon(3, "Venusaur", new Type[]{typeList.get("Grass"),typeList.get("Poison")},80,82,83,100,100,80);
            new Pokemon(3221, "Mega Venusaur", new Type[]{typeList.get("Grass"),typeList.get("Poison")},80,100,123,122,120,80);
            new Pokemon(4, "Charmander", new Type[]{typeList.get("Fire")},39,52,43,60,50,65);
            new Pokemon(5, "Charmeleon", new Type[]{typeList.get("Fire")},58,64,58,80,65,80);
            new Pokemon(6, "Charizard", new Type[]{typeList.get("Fire"),typeList.get("Flying")},78,84,78,109,85,100);
            new Pokemon(6321, "Mega Charizard X", new Type[]{typeList.get("Fire"),typeList.get("Dragon")},78,130,111,130,85,100);
            new Pokemon(6221, "Mega Charizard Y", new Type[]{typeList.get("Fire"),typeList.get("Flying")},78,104,78,159,115,100);
            new Pokemon(7, "Squirtle", new Type[]{typeList.get("Water")},44,48,65,50,64,43);
            new Pokemon(8, "Wartortle", new Type[]{typeList.get("Water")},59,63,80,65,80,58);
            new Pokemon(9, "Blastoise", new Type[]{typeList.get("Water")},79,83,100,85,105,78);
            new Pokemon(9221, "Mega Blastoise", new Type[]{typeList.get("Water")},79,103,120,135,115,78);
            new Pokemon(10, "Caterpie", new Type[]{typeList.get("Bug")},45,30,35,20,20,45);
            new Pokemon(11, "Metapod", new Type[]{typeList.get("Bug")},50,20,55,25,25,30);
            new Pokemon(12, "Butterfree", new Type[]{typeList.get("Bug"),typeList.get("Flying")},60,45,50,90,80,70);
            new Pokemon(13, "Weedle", new Type[]{typeList.get("Bug"),typeList.get("Poison")},40,35,30,20,20,50);
            new Pokemon(14, "Kakuna", new Type[]{typeList.get("Bug"),typeList.get("Poison")},45,25,50,25,25,35);
            new Pokemon(15, "Beedrill", new Type[]{typeList.get("Bug"),typeList.get("Poison")},65,90,40,45,80,75);
            new Pokemon(1521, "Mega Beedrill", new Type[]{typeList.get("Bug"),typeList.get("Poison")},65,150,40,15,80,145);
            new Pokemon(16, "Pidgey", new Type[]{typeList.get("Normal"),typeList.get("Flying")},40,45,40,35,35,56);
            new Pokemon(17, "Pidgeotto", new Type[]{typeList.get("Normal"),typeList.get("Flying")},63,60,55,50,50,71);
            new Pokemon(18, "Pidgeot", new Type[]{typeList.get("Normal"),typeList.get("Flying")},83,80,75,70,70,101);
            new Pokemon(1821, "Mega Pidgeot", new Type[]{typeList.get("Normal"),typeList.get("Flying")},83,80,80,135,80,121);
            new Pokemon(19, "Rattata", new Type[]{typeList.get("Normal")},30,56,35,25,35,72);
            new Pokemon(1922, "Alolan Rattata", new Type[]{typeList.get("Dark"),typeList.get("Normal")},30,56,35,25,35,72);
            new Pokemon(20, "Raticate", new Type[]{typeList.get("Normal")},55,81,60,50,70,97);
            new Pokemon(2022, "Alolan Raticate", new Type[]{typeList.get("Dark"),typeList.get("Normal")},75,71,70,40,80,77);
            new Pokemon(21, "Spearow", new Type[]{typeList.get("Normal"),typeList.get("Flying")},40,60,30,31,31,70);
            new Pokemon(22, "Fearow", new Type[]{typeList.get("Normal"),typeList.get("Flying")},65,90,65,61,61,100);
            new Pokemon(23, "Ekans", new Type[]{typeList.get("Poison")},35,60,44,40,54,55);
            new Pokemon(24, "Arbok", new Type[]{typeList.get("Poison")},60,95,69,65,79,80);
            new Pokemon(25, "Pikachu", new Type[]{typeList.get("Electric")},35,55,40,50,50,90);
            new Pokemon(26, "Raichu", new Type[]{typeList.get("Electric")},60,90,55,90,80,110);
            new Pokemon(2622, "Alolan Raichu", new Type[]{typeList.get("Electric"),typeList.get("Psychic")},60,85,50,95,85,110);
            new Pokemon(27, "Sandshrew", new Type[]{typeList.get("Ground")},50,75,85,20,30,40);
            new Pokemon(2722, "Alolan Sandshrew", new Type[]{typeList.get("Ice"),typeList.get("Steel")},50,75,90,10,35,40);
            new Pokemon(28, "Sandslash", new Type[]{typeList.get("Ground")},75,100,110,45,55,65);
            new Pokemon(2822, "Alolan Sandslash", new Type[]{typeList.get("Ice"),typeList.get("Steel")},75,100,120,25,65,65);
            new Pokemon(29, "Nidoran♀", new Type[]{typeList.get("Poison")},55,47,52,40,40,41);
            new Pokemon(30, "Nidorina", new Type[]{typeList.get("Poison")},70,62,67,55,55,56);
            new Pokemon(31, "Nidoqueen", new Type[]{typeList.get("Poison"),typeList.get("Ground")},90,92,87,75,85,76);
            new Pokemon(32, "Nidoran♂", new Type[]{typeList.get("Poison")},46,57,40,40,40,50);
            new Pokemon(33, "Nidorino", new Type[]{typeList.get("Poison")},61,72,57,55,55,65);
            new Pokemon(34, "Nidoking", new Type[]{typeList.get("Poison"),typeList.get("Ground")},81,102,77,85,75,85);
            new Pokemon(35, "Clefairy", new Type[]{typeList.get("Fairy")},70,45,48,60,65,35);
            new Pokemon(36, "Clefable", new Type[]{typeList.get("Fairy")},95,70,73,95,90,60);
            new Pokemon(37, "Vulpix", new Type[]{typeList.get("Fire")},38,41,40,50,65,65);
            new Pokemon(3722, "Alolan Vulpix", new Type[]{typeList.get("Ice")},38,41,40,50,65,65);
            new Pokemon(38, "Ninetales", new Type[]{typeList.get("Fire")},73,76,75,81,100,100);
            new Pokemon(3822, "Alolan Ninetales", new Type[]{typeList.get("Ice"),typeList.get("Fairy")},73,67,75,81,100,109);
            new Pokemon(39, "Jigglypuff", new Type[]{typeList.get("Normal"),typeList.get("Fairy")},115,45,20,45,25,20);
            new Pokemon(40, "Wigglytuff", new Type[]{typeList.get("Normal"),typeList.get("Fairy")},140,70,45,85,50,45);
            new Pokemon(41, "Zubat", new Type[]{typeList.get("Poison"),typeList.get("Flying")},40,45,35,30,40,55);
            new Pokemon(42, "Golbat", new Type[]{typeList.get("Poison"),typeList.get("Flying")},75,80,70,65,75,90);
            new Pokemon(43, "Oddish", new Type[]{typeList.get("Grass"),typeList.get("Poison")},45,50,55,75,65,30);
            new Pokemon(44, "Gloom", new Type[]{typeList.get("Grass"),typeList.get("Poison")},60,65,70,85,75,40);
            new Pokemon(45, "Vileplume", new Type[]{typeList.get("Grass"),typeList.get("Poison")},75,80,85,110,90,50);
            new Pokemon(46, "Paras", new Type[]{typeList.get("Bug"),typeList.get("Grass")},35,70,55,45,55,25);
            new Pokemon(47, "Parasect", new Type[]{typeList.get("Bug"),typeList.get("Grass")},60,95,80,60,80,30);
            new Pokemon(48, "Venonat", new Type[]{typeList.get("Bug"),typeList.get("Poison")},60,55,50,40,55,45);
            new Pokemon(49, "Venomoth", new Type[]{typeList.get("Bug"),typeList.get("Poison")},70,65,60,90,75,90);
            new Pokemon(50, "Diglett", new Type[]{typeList.get("Ground")},10,55,25,35,45,95);
            new Pokemon(5022, "Alolan Diglett", new Type[]{typeList.get("Ground"),typeList.get("Steel")},10,55,30,35,45,90);
            new Pokemon(51, "Dugtrio", new Type[]{typeList.get("Ground")},35,100,50,50,70,120);
            new Pokemon(5122, "Alolan Dugtrio", new Type[]{typeList.get("Ground"),typeList.get("Steel")},35,100,60,50,70,110);
            new Pokemon(52, "Meowth", new Type[]{typeList.get("Normal")},40,45,35,40,40,90);
            new Pokemon(5222, "Alolan Meowth", new Type[]{typeList.get("Dark")},40,35,35,50,40,90);
            new Pokemon(5223, "Galarian Meowth", new Type[]{typeList.get("Steel")},50,65,55,40,40,40);
            new Pokemon(53, "Persian", new Type[]{typeList.get("Normal")},65,70,60,65,65,115);
            new Pokemon(5322, "Alolan Persian", new Type[]{typeList.get("Dark")},65,60,60,75,65,115);
            new Pokemon(54, "Psyduck", new Type[]{typeList.get("Water")},50,52,48,65,50,55);
            new Pokemon(55, "Golduck", new Type[]{typeList.get("Water")},80,82,78,95,80,85);
            new Pokemon(56, "Mankey", new Type[]{typeList.get("Fighting")},40,80,35,35,45,70);
            new Pokemon(57, "Primeape", new Type[]{typeList.get("Fighting")},65,105,60,60,70,95);
            new Pokemon(58, "Growlithe", new Type[]{typeList.get("Fire")},55,70,45,70,50,60);
            new Pokemon(5824, "Hisuian Growlithe", new Type[]{typeList.get("Fire"),typeList.get("Rock")},60,75,45,65,50,55);
            new Pokemon(59, "Arcanine", new Type[]{typeList.get("Fire")},90,110,80,100,80,95);
            new Pokemon(5924, "Hisuian Arcanine", new Type[]{typeList.get("Fire"),typeList.get("Rock")},95,115,80,95,80,90);
            new Pokemon(60, "Poliwag", new Type[]{typeList.get("Water")},40,50,40,40,40,90);
            new Pokemon(61, "Poliwhirl", new Type[]{typeList.get("Water")},65,65,65,50,50,90);
            new Pokemon(62, "Poliwrath", new Type[]{typeList.get("Water"),typeList.get("Fighting")},90,95,95,70,90,70);
            new Pokemon(63, "Abra", new Type[]{typeList.get("Psychic")},25,20,15,105,55,90);
            new Pokemon(64, "Kadabra", new Type[]{typeList.get("Psychic")},40,35,30,120,70,105);
            new Pokemon(65, "Alakazam", new Type[]{typeList.get("Psychic")},55,50,45,135,95,120);
            new Pokemon(6521, "Mega Alakazam", new Type[]{typeList.get("Psychic")},55,50,65,175,105,150);
            new Pokemon(66, "Machop", new Type[]{typeList.get("Fighting")},70,80,50,35,35,35);
            new Pokemon(67, "Machoke", new Type[]{typeList.get("Fighting")},80,100,70,50,60,45);
            new Pokemon(68, "Machamp", new Type[]{typeList.get("Fighting")},90,130,80,65,85,55);
            new Pokemon(69, "Bellsprout", new Type[]{typeList.get("Grass"),typeList.get("Poison")},50,75,35,70,30,40);
            new Pokemon(70, "Weepinbell", new Type[]{typeList.get("Grass"),typeList.get("Poison")},65,90,50,85,45,55);
            new Pokemon(71, "Victreebel", new Type[]{typeList.get("Grass"),typeList.get("Poison")},80,105,65,100,70,70);
            new Pokemon(72, "Tentacool", new Type[]{typeList.get("Water"),typeList.get("Poison")},40,40,35,50,100,70);
            new Pokemon(73, "Tentacruel", new Type[]{typeList.get("Water"),typeList.get("Poison")},80,70,65,80,120,100);
            new Pokemon(74, "Geodude", new Type[]{typeList.get("Rock"),typeList.get("Ground")},40,80,100,30,30,20);
            new Pokemon(7422, "Alolan Geodude", new Type[]{typeList.get("Rock"),typeList.get("Electric")},40,80,100,30,30,20);
            new Pokemon(75, "Graveler", new Type[]{typeList.get("Rock"),typeList.get("Ground")},55,95,115,45,45,35);
            new Pokemon(7522, "Alolan Graveler", new Type[]{typeList.get("Rock"),typeList.get("Electric")},55,95,115,45,45,35);
            new Pokemon(76, "Golem", new Type[]{typeList.get("Rock"),typeList.get("Ground")},80,120,130,55,65,45);
            new Pokemon(7622, "Alolan Golem", new Type[]{typeList.get("Rock"),typeList.get("Electric")},80,120,130,55,65,45);
            new Pokemon(77, "Ponyta", new Type[]{typeList.get("Fire")},50,85,55,65,65,90);
            new Pokemon(7723, "Galarian Ponyta", new Type[]{typeList.get("Psychic")},50,85,55,65,65,90);
            new Pokemon(78, "Rapidash", new Type[]{typeList.get("Fire")},65,100,70,80,80,105);
            new Pokemon(7823, "Galarian Rapidash", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},65,100,70,80,80,105);
            new Pokemon(79, "Slowpoke", new Type[]{typeList.get("Water"),typeList.get("Psychic")},90,65,65,40,40,15);
            new Pokemon(7923, "Galarian Slowpoke", new Type[]{typeList.get("Psychic")},90,65,65,40,40,15);
            new Pokemon(80, "Slowbro", new Type[]{typeList.get("Water"),typeList.get("Psychic")},95,75,110,100,80,30);
            new Pokemon(8021, "Mega Slowbro", new Type[]{typeList.get("Water"),typeList.get("Psychic")},95,75,180,130,80,30);
            new Pokemon(8023, "Galarian Slowbro", new Type[]{typeList.get("Poison"),typeList.get("Psychic")},95,100,95,100,70,30);
            new Pokemon(81, "Magnemite", new Type[]{typeList.get("Electric"),typeList.get("Steel")},25,35,70,95,55,45);
            new Pokemon(82, "Magneton", new Type[]{typeList.get("Electric"),typeList.get("Steel")},50,60,95,120,70,70);
            new Pokemon(83, "Farfetch'd", new Type[]{typeList.get("Normal"),typeList.get("Flying")},52,90,55,58,62,60);
            new Pokemon(8323, "Galarian Farfetch'd", new Type[]{typeList.get("Fighting")},52,95,55,58,62,55);
            new Pokemon(84, "Doduo", new Type[]{typeList.get("Normal"),typeList.get("Flying")},35,85,45,35,35,75);
            new Pokemon(85, "Dodrio", new Type[]{typeList.get("Normal"),typeList.get("Flying")},60,110,70,60,60,110);
            new Pokemon(86, "Seel", new Type[]{typeList.get("Water")},65,45,55,45,70,45);
            new Pokemon(87, "Dewgong", new Type[]{typeList.get("Water"),typeList.get("Ice")},90,70,80,70,95,70);
            new Pokemon(88, "Grimer", new Type[]{typeList.get("Poison")},80,80,50,40,50,25);
            new Pokemon(8822, "Alolan Grimer", new Type[]{typeList.get("Poison"),typeList.get("Dark")},80,80,50,40,50,25);
            new Pokemon(89, "Muk", new Type[]{typeList.get("Poison")},105,105,75,65,100,50);
            new Pokemon(8922, "Alolan Muk", new Type[]{typeList.get("Poison"),typeList.get("Dark")},105,105,75,65,100,50);
            new Pokemon(90, "Shellder", new Type[]{typeList.get("Water")},30,65,100,45,25,40);
            new Pokemon(91, "Cloyster", new Type[]{typeList.get("Water"),typeList.get("Ice")},50,95,180,85,45,70);
            new Pokemon(92, "Gastly", new Type[]{typeList.get("Ghost"),typeList.get("Poison")},30,35,30,100,35,80);
            new Pokemon(93, "Haunter", new Type[]{typeList.get("Ghost"),typeList.get("Poison")},45,50,45,115,55,95);
            new Pokemon(94, "Gengar", new Type[]{typeList.get("Ghost"),typeList.get("Poison")},60,65,60,130,75,110);
            new Pokemon(9421, "Mega Gengar", new Type[]{typeList.get("Ghost"),typeList.get("Poison")},60,65,80,170,95,130);
            new Pokemon(95, "Onix", new Type[]{typeList.get("Rock"),typeList.get("Ground")},35,45,160,30,45,70);
            new Pokemon(96, "Drowzee", new Type[]{typeList.get("Psychic")},60,48,45,43,90,42);
            new Pokemon(97, "Hypno", new Type[]{typeList.get("Psychic")},85,73,70,73,115,67);
            new Pokemon(98, "Krabby", new Type[]{typeList.get("Water")},30,105,90,25,25,50);
            new Pokemon(99, "Kingler", new Type[]{typeList.get("Water")},55,130,115,50,50,75);
            new Pokemon(100, "Voltorb", new Type[]{typeList.get("Electric")},40,30,50,55,55,100);
            new Pokemon(10024, "Hisuian Voltorb", new Type[]{typeList.get("Electric"),typeList.get("Grass")},40,30,50,55,55,100);
            new Pokemon(101, "Electrode", new Type[]{typeList.get("Electric")},60,50,70,80,80,150);
            new Pokemon(101, "Hisuian Electrode", new Type[]{typeList.get("Electric"),typeList.get("Grass")},60,50,70,80,80,150);
            new Pokemon(102, "Exeggcute", new Type[]{typeList.get("Grass"),typeList.get("Psychic")},60,40,80,60,45,40);
            new Pokemon(103, "Exeggutor", new Type[]{typeList.get("Grass"),typeList.get("Psychic")},95,95,85,125,75,55);
            new Pokemon(103, "Alolan Exeggutor", new Type[]{typeList.get("Grass"),typeList.get("Dragon")},95,105,85,125,75,45);
            new Pokemon(104, "Cubone", new Type[]{typeList.get("Ground")},50,50,95,40,50,35);
            new Pokemon(105, "Marowak", new Type[]{typeList.get("Ground")},60,80,110,50,80,45);
            new Pokemon(105, "Alolan Marowak", new Type[]{typeList.get("Fire"),typeList.get("Ghost")},60,80,110,50,80,45);
            new Pokemon(106, "Hitmonlee", new Type[]{typeList.get("Fighting")},50,120,53,35,110,87);
            new Pokemon(107, "Hitmonchan", new Type[]{typeList.get("Fighting")},50,105,79,35,110,76);
            new Pokemon(108, "Lickitung", new Type[]{typeList.get("Normal")},90,55,75,60,75,30);
            new Pokemon(109, "Koffing", new Type[]{typeList.get("Poison")},40,65,95,60,45,35);
            new Pokemon(110, "Weezing", new Type[]{typeList.get("Poison")},65,90,120,85,70,60);
            new Pokemon(110, "Galarian Weezing", new Type[]{typeList.get("Poison"),typeList.get("Fairy")},65,90,120,85,70,60);
            new Pokemon(111, "Rhyhorn", new Type[]{typeList.get("Ground"),typeList.get("Rock")},80,85,95,30,30,25);
            new Pokemon(112, "Rhydon", new Type[]{typeList.get("Ground"),typeList.get("Rock")},105,130,120,45,45,40);
            new Pokemon(113, "Chansey", new Type[]{typeList.get("Normal")},250,5,5,35,105,50);
            new Pokemon(114, "Tangela", new Type[]{typeList.get("Grass")},65,55,115,100,40,60);
            new Pokemon(115, "Kangaskhan", new Type[]{typeList.get("Normal")},105,95,80,40,80,90);
            new Pokemon(115, "Mega Kangaskhan", new Type[]{typeList.get("Normal")},105,125,100,60,100,100);
            new Pokemon(116, "Horsea", new Type[]{typeList.get("Water")},30,40,70,70,25,60);
            new Pokemon(117, "Seadra", new Type[]{typeList.get("Water")},55,65,95,95,45,85);
            new Pokemon(118, "Goldeen", new Type[]{typeList.get("Water")},45,67,60,35,50,63);
            new Pokemon(119, "Seaking", new Type[]{typeList.get("Water")},80,92,65,65,80,68);
            new Pokemon(120, "Staryu", new Type[]{typeList.get("Water")},30,45,55,70,55,85);
            new Pokemon(121, "Starmie", new Type[]{typeList.get("Water"),typeList.get("Psychic")},60,75,85,100,85,115);
            new Pokemon(122, "Mr. Mime", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},40,45,65,100,120,90);
            new Pokemon(122, "Galarian Mr. Mime", new Type[]{typeList.get("Ice"),typeList.get("Psychic")},50,65,65,90,90,100);
            new Pokemon(123, "Scyther", new Type[]{typeList.get("Bug"),typeList.get("Flying")},70,110,80,55,80,105);
            new Pokemon(124, "Jynx", new Type[]{typeList.get("Ice"),typeList.get("Psychic")},65,50,35,115,95,95);
            new Pokemon(125, "Electabuzz", new Type[]{typeList.get("Electric")},65,83,57,95,85,105);
            new Pokemon(126, "Magmar", new Type[]{typeList.get("Fire")},65,95,57,100,85,93);
            new Pokemon(127, "Pinsir", new Type[]{typeList.get("Bug")},65,125,100,55,70,85);
            new Pokemon(127, "Mega Pinsir", new Type[]{typeList.get("Bug"),typeList.get("Flying")},65,155,120,65,90,105);
            new Pokemon(128, "Tauros", new Type[]{typeList.get("Normal")},75,100,95,40,70,110);
            new Pokemon(128, "Paldean Tauros (Combat Breed)", new Type[]{typeList.get("Fighting")},75,110,105,30,70,100);
            new Pokemon(128, "Paldean Tauros (Blaze Breed)", new Type[]{typeList.get("Fighting"),typeList.get("Fire")},75,110,105,30,70,100);
            new Pokemon(128, "Paldean Tauros (Aqua Breed)", new Type[]{typeList.get("Fighting"),typeList.get("Water")},75,110,105,30,70,100);
            new Pokemon(129, "Magikarp", new Type[]{typeList.get("Water")},20,10,55,15,20,80);
            new Pokemon(130, "Gyarados", new Type[]{typeList.get("Water"),typeList.get("Flying")},95,125,79,60,100,81);
            new Pokemon(130, "Mega Gyarados", new Type[]{typeList.get("Water"),typeList.get("Dark")},95,155,109,70,130,81);
            new Pokemon(131, "Lapras", new Type[]{typeList.get("Water"),typeList.get("Ice")},130,85,80,85,95,60);
            new Pokemon(132, "Ditto", new Type[]{typeList.get("Normal")},48,48,48,48,48,48);
            new Pokemon(133, "Eevee", new Type[]{typeList.get("Normal")},55,55,50,45,65,55);
            new Pokemon(134, "Vaporeon", new Type[]{typeList.get("Water")},130,65,60,110,95,65);
            new Pokemon(135, "Jolteon", new Type[]{typeList.get("Electric")},65,65,60,110,95,130);
            new Pokemon(136, "Flareon", new Type[]{typeList.get("Fire")},65,130,60,95,110,65);
            new Pokemon(137, "Porygon", new Type[]{typeList.get("Normal")},65,60,70,85,75,40);
            new Pokemon(138, "Omanyte", new Type[]{typeList.get("Rock"),typeList.get("Water")},35,40,100,90,55,35);
            new Pokemon(139, "Omastar", new Type[]{typeList.get("Rock"),typeList.get("Water")},70,60,125,115,70,55);
            new Pokemon(140, "Kabuto", new Type[]{typeList.get("Rock"),typeList.get("Water")},30,80,90,55,45,55);
            new Pokemon(141, "Kabutops", new Type[]{typeList.get("Rock"),typeList.get("Water")},60,115,105,65,70,80);
            new Pokemon(142, "Aerodactyl", new Type[]{typeList.get("Rock"),typeList.get("Flying")},80,105,65,60,75,130);
            new Pokemon(142, "Mega Aerodactyl", new Type[]{typeList.get("Rock"),typeList.get("Flying")},80,135,85,70,95,150);
            new Pokemon(143, "Snorlax", new Type[]{typeList.get("Normal")},160,110,65,65,110,30);
            new Pokemon(144, "Articuno", new Type[]{typeList.get("Ice"),typeList.get("Flying")},90,85,100,95,125,85);
            new Pokemon(144, "Galarian Articuno", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},90,85,85,125,100,95);
            new Pokemon(145, "Zapdos", new Type[]{typeList.get("Electric"),typeList.get("Flying")},90,90,85,125,90,100);
            new Pokemon(145, "Galarian Zapdos", new Type[]{typeList.get("Fighting"),typeList.get("Flying")},90,125,90,85,90,100);
            new Pokemon(146, "Moltres", new Type[]{typeList.get("Fire"),typeList.get("Flying")},90,100,90,125,85,90);
            new Pokemon(146, "Galarian Moltres", new Type[]{typeList.get("Dark"),typeList.get("Flying")},90,85,90,100,125,90);
            new Pokemon(147, "Dratini", new Type[]{typeList.get("Dragon")},41,64,45,50,50,50);
            new Pokemon(148, "Dragonair", new Type[]{typeList.get("Dragon")},61,84,65,70,70,70);
            new Pokemon(149, "Dragonite", new Type[]{typeList.get("Dragon"),typeList.get("Flying")},91,134,95,100,100,80);
            new Pokemon(150, "Mewtwo", new Type[]{typeList.get("Psychic")},106,110,90,154,90,130);
            new Pokemon(150, "Mega Mewtwo X", new Type[]{typeList.get("Psychic"),typeList.get("Fighting")},106,190,100,154,100,130);
            new Pokemon(150, "Mega Mewtwo Y", new Type[]{typeList.get("Psychic")},106,150,70,194,120,140);
            new Pokemon(151, "Mew", new Type[]{typeList.get("Psychic")},100,100,100,100,100,100);
            new Pokemon(152, "Chikorita", new Type[]{typeList.get("Grass")},45,49,65,49,65,45);
            new Pokemon(153, "Bayleef", new Type[]{typeList.get("Grass")},60,62,80,63,80,60);
            new Pokemon(154, "Meganium", new Type[]{typeList.get("Grass")},80,82,100,83,100,80);
            new Pokemon(155, "Cyndaquil", new Type[]{typeList.get("Fire")},39,52,43,60,50,65);
            new Pokemon(156, "Quilava", new Type[]{typeList.get("Fire")},58,64,58,80,65,80);
            new Pokemon(157, "Typhlosion", new Type[]{typeList.get("Fire")},78,84,78,109,85,100);
            new Pokemon(157, "Hisuian Typhlosion", new Type[]{typeList.get("Fire"),typeList.get("Ghost")},73,84,78,119,85,95);
            new Pokemon(158, "Totodile", new Type[]{typeList.get("Water")},50,65,64,44,48,43);
            new Pokemon(159, "Croconaw", new Type[]{typeList.get("Water")},65,80,80,59,63,58);
            new Pokemon(160, "Feraligatr", new Type[]{typeList.get("Water")},85,105,100,79,83,78);
            new Pokemon(161, "Sentret", new Type[]{typeList.get("Normal")},35,46,34,35,45,20);
            new Pokemon(162, "Furret", new Type[]{typeList.get("Normal")},85,76,64,45,55,90);
            new Pokemon(163, "Hoothoot", new Type[]{typeList.get("Normal"),typeList.get("Flying")},60,30,30,36,56,50);
            new Pokemon(164, "Noctowl", new Type[]{typeList.get("Normal"),typeList.get("Flying")},100,50,50,86,96,70);
            new Pokemon(165, "Ledyba", new Type[]{typeList.get("Bug"),typeList.get("Flying")},40,20,30,40,80,55);
            new Pokemon(166, "Ledian", new Type[]{typeList.get("Bug"),typeList.get("Flying")},55,35,50,55,110,85);
            new Pokemon(167, "Spinarak", new Type[]{typeList.get("Bug"),typeList.get("Poison")},40,60,40,40,40,30);
            new Pokemon(168, "Ariados", new Type[]{typeList.get("Bug"),typeList.get("Poison")},70,90,70,60,70,40);
            new Pokemon(169, "Crobat", new Type[]{typeList.get("Poison"),typeList.get("Flying")},85,90,80,70,80,130);
            new Pokemon(170, "Chinchou", new Type[]{typeList.get("Water"),typeList.get("Electric")},75,38,38,56,56,67);
            new Pokemon(171, "Lanturn", new Type[]{typeList.get("Water"),typeList.get("Electric")},125,58,58,76,76,67);
            new Pokemon(172, "Pichu", new Type[]{typeList.get("Electric")},20,40,15,35,35,60);
            new Pokemon(173, "Cleffa", new Type[]{typeList.get("Fairy")},50,25,28,45,55,15);
            new Pokemon(174, "Igglybuff", new Type[]{typeList.get("Normal"),typeList.get("Fairy")},90,30,15,40,20,15);
            new Pokemon(175, "Togepi", new Type[]{typeList.get("Fairy")},35,20,65,40,65,20);
            new Pokemon(176, "Togetic", new Type[]{typeList.get("Fairy"),typeList.get("Flying")},55,40,85,80,105,40);
            new Pokemon(177, "Natu", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},40,50,45,70,45,70);
            new Pokemon(178, "Xatu", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},65,75,70,95,70,95);
            new Pokemon(179, "Mareep", new Type[]{typeList.get("Electric")},55,40,40,65,45,35);
            new Pokemon(180, "Flaaffy", new Type[]{typeList.get("Electric")},70,55,55,80,60,45);
            new Pokemon(181, "Ampharos", new Type[]{typeList.get("Electric")},90,75,85,115,90,55);
            new Pokemon(181, "Mega Ampharos", new Type[]{typeList.get("Electric"),typeList.get("Dragon")},90,95,105,165,110,45);
            new Pokemon(182, "Bellossom", new Type[]{typeList.get("Grass")},75,80,95,90,100,50);
            new Pokemon(183, "Marill", new Type[]{typeList.get("Water"),typeList.get("Fairy")},70,20,50,20,50,40);
            new Pokemon(184, "Azumarill", new Type[]{typeList.get("Water"),typeList.get("Fairy")},100,50,80,60,80,50);
            new Pokemon(185, "Sudowoodo", new Type[]{typeList.get("Rock")},70,100,115,30,65,30);
            new Pokemon(186, "Politoed", new Type[]{typeList.get("Water")},90,75,75,90,100,70);
            new Pokemon(187, "Hoppip", new Type[]{typeList.get("Grass"),typeList.get("Flying")},35,35,40,35,55,50);
            new Pokemon(188, "Skiploom", new Type[]{typeList.get("Grass"),typeList.get("Flying")},55,45,50,45,65,80);
            new Pokemon(189, "Jumpluff", new Type[]{typeList.get("Grass"),typeList.get("Flying")},75,55,70,55,95,110);
            new Pokemon(190, "Aipom", new Type[]{typeList.get("Normal")},55,70,55,40,55,85);
            new Pokemon(191, "Sunkern", new Type[]{typeList.get("Grass")},30,30,30,30,30,30);
            new Pokemon(192, "Sunflora", new Type[]{typeList.get("Grass")},75,75,55,105,85,30);
            new Pokemon(193, "Yanma", new Type[]{typeList.get("Bug"),typeList.get("Flying")},65,65,45,75,45,95);
            new Pokemon(194, "Wooper", new Type[]{typeList.get("Water"),typeList.get("Ground")},55,45,45,25,25,15);
            new Pokemon(194, "Paldean Wooper", new Type[]{typeList.get("Poison"),typeList.get("Ground")},55,45,45,25,25,15);
            new Pokemon(195, "Quagsire", new Type[]{typeList.get("Water"),typeList.get("Ground")},95,85,85,65,65,35);
            new Pokemon(196, "Espeon", new Type[]{typeList.get("Psychic")},65,65,60,130,95,110);
            new Pokemon(197, "Umbreon", new Type[]{typeList.get("Dark")},95,65,110,60,130,65);
            new Pokemon(198, "Murkrow", new Type[]{typeList.get("Dark"),typeList.get("Flying")},60,85,42,85,42,91);
            new Pokemon(199, "Slowking", new Type[]{typeList.get("Water"),typeList.get("Psychic")},95,75,80,100,110,30);
            new Pokemon(199, "Galarian Slowking", new Type[]{typeList.get("Poison"),typeList.get("Psychic")},95,65,80,110,110,30);
            new Pokemon(200, "Misdreavus", new Type[]{typeList.get("Ghost")},60,60,60,85,85,85);
            new Pokemon(201, "Unown", new Type[]{typeList.get("Psychic")},48,72,48,72,48,48);
            new Pokemon(202, "Wobbuffet", new Type[]{typeList.get("Psychic")},190,33,58,33,58,33);
            new Pokemon(203, "Girafarig", new Type[]{typeList.get("Normal"),typeList.get("Psychic")},70,80,65,90,65,85);
            new Pokemon(204, "Pineco", new Type[]{typeList.get("Bug")},50,65,90,35,35,15);
            new Pokemon(205, "Forretress", new Type[]{typeList.get("Bug"),typeList.get("Steel")},75,90,140,60,60,40);
            new Pokemon(206, "Dunsparce", new Type[]{typeList.get("Normal")},100,70,70,65,65,45);
            new Pokemon(207, "Gligar", new Type[]{typeList.get("Ground"),typeList.get("Flying")},65,75,105,35,65,85);
            new Pokemon(208, "Steelix", new Type[]{typeList.get("Steel"),typeList.get("Ground")},75,85,200,55,65,30);
            new Pokemon(208, "Mega Steelix", new Type[]{typeList.get("Steel"),typeList.get("Ground")},75,125,230,55,95,30);
            new Pokemon(209, "Snubbull", new Type[]{typeList.get("Fairy")},60,80,50,40,40,30);
            new Pokemon(210, "Granbull", new Type[]{typeList.get("Fairy")},90,120,75,60,60,45);
            new Pokemon(211, "Qwilfish", new Type[]{typeList.get("Water"),typeList.get("Poison")},65,95,85,55,55,85);
            new Pokemon(211, "Hisuian Qwilfish", new Type[]{typeList.get("Dark"),typeList.get("Poison")},65,95,85,55,55,85);
            new Pokemon(212, "Scizor", new Type[]{typeList.get("Bug"),typeList.get("Steel")},70,130,100,55,80,65);
            new Pokemon(212, "Mega Scizor", new Type[]{typeList.get("Bug"),typeList.get("Steel")},70,150,140,65,100,75);
            new Pokemon(213, "Shuckle", new Type[]{typeList.get("Bug"),typeList.get("Rock")},20,10,230,10,230,5);
            new Pokemon(214, "Heracross", new Type[]{typeList.get("Bug"),typeList.get("Fighting")},80,125,75,40,95,85);
            new Pokemon(214, "Mega Heracross", new Type[]{typeList.get("Bug"),typeList.get("Fighting")},80,185,115,40,105,75);
            new Pokemon(215, "Sneasel", new Type[]{typeList.get("Dark"),typeList.get("Ice")},55,95,55,35,75,115);
            new Pokemon(215, "Hisuian Sneasel", new Type[]{typeList.get("Fighting"),typeList.get("Poison")},55,95,55,35,75,115);
            new Pokemon(216, "Teddiursa", new Type[]{typeList.get("Normal")},60,80,50,50,50,40);
            new Pokemon(217, "Ursaring", new Type[]{typeList.get("Normal")},90,130,75,75,75,55);
            new Pokemon(218, "Slugma", new Type[]{typeList.get("Fire")},40,40,40,70,40,20);
            new Pokemon(219, "Magcargo", new Type[]{typeList.get("Fire"),typeList.get("Rock")},60,50,120,90,80,30);
            new Pokemon(220, "Swinub", new Type[]{typeList.get("Ice"),typeList.get("Ground")},50,50,40,30,30,50);
            new Pokemon(221, "Piloswine", new Type[]{typeList.get("Ice"),typeList.get("Ground")},100,100,80,60,60,50);
            new Pokemon(222, "Corsola", new Type[]{typeList.get("Water"),typeList.get("Rock")},65,55,95,65,95,35);
            new Pokemon(222, "Galarian Corsola", new Type[]{typeList.get("Ghost")},60,55,100,65,100,30);
            new Pokemon(223, "Remoraid", new Type[]{typeList.get("Water")},35,65,35,65,35,65);
            new Pokemon(224, "Octillery", new Type[]{typeList.get("Water")},75,105,75,105,75,45);
            new Pokemon(225, "Delibird", new Type[]{typeList.get("Ice"),typeList.get("Flying")},45,55,45,65,45,75);
            new Pokemon(226, "Mantine", new Type[]{typeList.get("Water"),typeList.get("Flying")},85,40,70,80,140,70);
            new Pokemon(227, "Skarmory", new Type[]{typeList.get("Steel"),typeList.get("Flying")},65,80,140,40,70,70);
            new Pokemon(228, "Houndour", new Type[]{typeList.get("Dark"),typeList.get("Fire")},45,60,30,80,50,65);
            new Pokemon(229, "Houndoom", new Type[]{typeList.get("Dark"),typeList.get("Fire")},75,90,50,110,80,95);
            new Pokemon(229, "Mega Houndoom", new Type[]{typeList.get("Dark"),typeList.get("Fire")},75,90,90,140,90,115);
            new Pokemon(230, "Kingdra", new Type[]{typeList.get("Water"),typeList.get("Dragon")},75,95,95,95,95,85);
            new Pokemon(231, "Phanpy", new Type[]{typeList.get("Ground")},90,60,60,40,40,40);
            new Pokemon(232, "Donphan", new Type[]{typeList.get("Ground")},90,120,120,60,60,50);
            new Pokemon(233, "Porygon2", new Type[]{typeList.get("Normal")},85,80,90,105,95,60);
            new Pokemon(234, "Stantler", new Type[]{typeList.get("Normal")},73,95,62,85,65,85);
            new Pokemon(235, "Smeargle", new Type[]{typeList.get("Normal")},55,20,35,20,45,75);
            new Pokemon(236, "Tyrogue", new Type[]{typeList.get("Fighting")},35,35,35,35,35,35);
            new Pokemon(237, "Hitmontop", new Type[]{typeList.get("Fighting")},50,95,95,35,110,70);
            new Pokemon(238, "Smoochum", new Type[]{typeList.get("Ice"),typeList.get("Psychic")},45,30,15,85,65,65);
            new Pokemon(239, "Elekid", new Type[]{typeList.get("Electric")},45,63,37,65,55,95);
            new Pokemon(240, "Magby", new Type[]{typeList.get("Fire")},45,75,37,70,55,83);
            new Pokemon(241, "Miltank", new Type[]{typeList.get("Normal")},95,80,105,40,70,100);
            new Pokemon(242, "Blissey", new Type[]{typeList.get("Normal")},255,10,10,75,135,55);
            new Pokemon(243, "Raikou", new Type[]{typeList.get("Electric")},90,85,75,115,100,115);
            new Pokemon(244, "Entei", new Type[]{typeList.get("Fire")},115,115,85,90,75,100);
            new Pokemon(245, "Suicune", new Type[]{typeList.get("Water")},100,75,115,90,115,85);
            new Pokemon(246, "Larvitar", new Type[]{typeList.get("Rock"),typeList.get("Ground")},50,64,50,45,50,41);
            new Pokemon(247, "Pupitar", new Type[]{typeList.get("Rock"),typeList.get("Ground")},70,84,70,65,70,51);
            new Pokemon(248, "Tyranitar", new Type[]{typeList.get("Rock"),typeList.get("Dark")},100,134,110,95,100,61);
            new Pokemon(248, "Mega Tyranitar", new Type[]{typeList.get("Rock"),typeList.get("Dark")},100,164,150,95,120,71);
            new Pokemon(249, "Lugia", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},106,90,130,90,154,110);
            new Pokemon(250, "Ho-Oh", new Type[]{typeList.get("Fire"),typeList.get("Flying")},106,130,90,110,154,90);
            new Pokemon(251, "Celebi", new Type[]{typeList.get("Psychic"),typeList.get("Grass")},100,100,100,100,100,100);
            new Pokemon(252, "Treecko", new Type[]{typeList.get("Grass")},40,45,35,65,55,70);
            new Pokemon(253, "Grovyle", new Type[]{typeList.get("Grass")},50,65,45,85,65,95);
            new Pokemon(254, "Sceptile", new Type[]{typeList.get("Grass")},70,85,65,105,85,120);
            new Pokemon(254, "Mega Sceptile", new Type[]{typeList.get("Grass"),typeList.get("Dragon")},70,110,75,145,85,145);
            new Pokemon(255, "Torchic", new Type[]{typeList.get("Fire")},45,60,40,70,50,45);
            new Pokemon(256, "Combusken", new Type[]{typeList.get("Fire"),typeList.get("Fighting")},60,85,60,85,60,55);
            new Pokemon(257, "Blaziken", new Type[]{typeList.get("Fire"),typeList.get("Fighting")},80,120,70,110,70,80);
            new Pokemon(257, "Mega Blaziken", new Type[]{typeList.get("Fire"),typeList.get("Fighting")},80,160,80,130,80,100);
            new Pokemon(258, "Mudkip", new Type[]{typeList.get("Water")},50,70,50,50,50,40);
            new Pokemon(259, "Marshtomp", new Type[]{typeList.get("Water"),typeList.get("Ground")},70,85,70,60,70,50);
            new Pokemon(260, "Swampert", new Type[]{typeList.get("Water"),typeList.get("Ground")},100,110,90,85,90,60);
            new Pokemon(260, "Mega Swampert", new Type[]{typeList.get("Water"),typeList.get("Ground")},100,150,110,95,110,70);
            new Pokemon(261, "Poochyena", new Type[]{typeList.get("Dark")},35,55,35,30,30,35);
            new Pokemon(262, "Mightyena", new Type[]{typeList.get("Dark")},70,90,70,60,60,70);
            new Pokemon(263, "Zigzagoon", new Type[]{typeList.get("Normal")},38,30,41,30,41,60);
            new Pokemon(263, "Galarian Zigzagoon", new Type[]{typeList.get("Dark"),typeList.get("Normal")},38,30,41,30,41,60);
            new Pokemon(264, "Linoone", new Type[]{typeList.get("Normal")},78,70,61,50,61,100);
            new Pokemon(264, "Galarian Linoone", new Type[]{typeList.get("Dark"),typeList.get("Normal")},78,70,61,50,61,100);
            new Pokemon(265, "Wurmple", new Type[]{typeList.get("Bug")},45,45,35,20,30,20);
            new Pokemon(266, "Silcoon", new Type[]{typeList.get("Bug")},50,35,55,25,25,15);
            new Pokemon(267, "Beautifly", new Type[]{typeList.get("Bug"),typeList.get("Flying")},60,70,50,100,50,65);
            new Pokemon(268, "Cascoon", new Type[]{typeList.get("Bug")},50,35,55,25,25,15);
            new Pokemon(269, "Dustox", new Type[]{typeList.get("Bug"),typeList.get("Poison")},60,50,70,50,90,65);
            new Pokemon(270, "Lotad", new Type[]{typeList.get("Water"),typeList.get("Grass")},40,30,30,40,50,30);
            new Pokemon(271, "Lombre", new Type[]{typeList.get("Water"),typeList.get("Grass")},60,50,50,60,70,50);
            new Pokemon(272, "Ludicolo", new Type[]{typeList.get("Water"),typeList.get("Grass")},80,70,70,90,100,70);
            new Pokemon(273, "Seedot", new Type[]{typeList.get("Grass")},40,40,50,30,30,30);
            new Pokemon(274, "Nuzleaf", new Type[]{typeList.get("Grass"),typeList.get("Dark")},70,70,40,60,40,60);
            new Pokemon(275, "Shiftry", new Type[]{typeList.get("Grass"),typeList.get("Dark")},90,100,60,90,60,80);
            new Pokemon(276, "Taillow", new Type[]{typeList.get("Normal"),typeList.get("Flying")},40,55,30,30,30,85);
            new Pokemon(277, "Swellow", new Type[]{typeList.get("Normal"),typeList.get("Flying")},60,85,60,75,50,125);
            new Pokemon(278, "Wingull", new Type[]{typeList.get("Water"),typeList.get("Flying")},40,30,30,55,30,85);
            new Pokemon(279, "Pelipper", new Type[]{typeList.get("Water"),typeList.get("Flying")},60,50,100,95,70,65);
            new Pokemon(280, "Ralts", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},28,25,25,45,35,40);
            new Pokemon(281, "Kirlia", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},38,35,35,65,55,50);
            new Pokemon(282, "Gardevoir", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},68,65,65,125,115,80);
            new Pokemon(282, "Mega Gardevoir", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},68,85,65,165,135,100);
            new Pokemon(283, "Surskit", new Type[]{typeList.get("Bug"),typeList.get("Water")},40,30,32,50,52,65);
            new Pokemon(284, "Masquerain", new Type[]{typeList.get("Bug"),typeList.get("Flying")},70,60,62,100,82,80);
            new Pokemon(285, "Shroomish", new Type[]{typeList.get("Grass")},60,40,60,40,60,35);
            new Pokemon(286, "Breloom", new Type[]{typeList.get("Grass"),typeList.get("Fighting")},60,130,80,60,60,70);
            new Pokemon(287, "Slakoth", new Type[]{typeList.get("Normal")},60,60,60,35,35,30);
            new Pokemon(288, "Vigoroth", new Type[]{typeList.get("Normal")},80,80,80,55,55,90);
            new Pokemon(289, "Slaking", new Type[]{typeList.get("Normal")},150,160,100,95,65,100);
            new Pokemon(290, "Nincada", new Type[]{typeList.get("Bug"),typeList.get("Ground")},31,45,90,30,30,40);
            new Pokemon(291, "Ninjask", new Type[]{typeList.get("Bug"),typeList.get("Flying")},61,90,45,50,50,160);
            new Pokemon(292, "Shedinja", new Type[]{typeList.get("Bug"),typeList.get("Ghost")},1,90,45,30,30,40);
            new Pokemon(293, "Whismur", new Type[]{typeList.get("Normal")},64,51,23,51,23,28);
            new Pokemon(294, "Loudred", new Type[]{typeList.get("Normal")},84,71,43,71,43,48);
            new Pokemon(295, "Exploud", new Type[]{typeList.get("Normal")},104,91,63,91,73,68);
            new Pokemon(296, "Makuhita", new Type[]{typeList.get("Fighting")},72,60,30,20,30,25);
            new Pokemon(297, "Hariyama", new Type[]{typeList.get("Fighting")},144,120,60,40,60,50);
            new Pokemon(298, "Azurill", new Type[]{typeList.get("Normal"),typeList.get("Fairy")},50,20,40,20,40,20);
            new Pokemon(299, "Nosepass", new Type[]{typeList.get("Rock")},30,45,135,45,90,30);
            new Pokemon(300, "Skitty", new Type[]{typeList.get("Normal")},50,45,45,35,35,50);
            new Pokemon(301, "Delcatty", new Type[]{typeList.get("Normal")},70,65,65,55,55,90);
            new Pokemon(302, "Sableye", new Type[]{typeList.get("Dark"),typeList.get("Ghost")},50,75,75,65,65,50);
            new Pokemon(302, "Mega Sableye", new Type[]{typeList.get("Dark"),typeList.get("Ghost")},50,85,125,85,115,20);
            new Pokemon(303, "Mawile", new Type[]{typeList.get("Steel"),typeList.get("Fairy")},50,85,85,55,55,50);
            new Pokemon(303, "Mega Mawile", new Type[]{typeList.get("Steel"),typeList.get("Fairy")},50,105,125,55,95,50);
            new Pokemon(304, "Aron", new Type[]{typeList.get("Steel"),typeList.get("Rock")},50,70,100,40,40,30);
            new Pokemon(305, "Lairon", new Type[]{typeList.get("Steel"),typeList.get("Rock")},60,90,140,50,50,40);
            new Pokemon(306, "Aggron", new Type[]{typeList.get("Steel"),typeList.get("Rock")},70,110,180,60,60,50);
            new Pokemon(306, "Mega Aggron", new Type[]{typeList.get("Steel")},70,140,230,60,80,50);
            new Pokemon(307, "Meditite", new Type[]{typeList.get("Fighting"),typeList.get("Psychic")},30,40,55,40,55,60);
            new Pokemon(308, "Medicham", new Type[]{typeList.get("Fighting"),typeList.get("Psychic")},60,60,75,60,75,80);
            new Pokemon(308, "Mega Medicham", new Type[]{typeList.get("Fighting"),typeList.get("Psychic")},60,100,85,80,85,100);
            new Pokemon(309, "Electrike", new Type[]{typeList.get("Electric")},40,45,40,65,40,65);
            new Pokemon(310, "Manectric", new Type[]{typeList.get("Electric")},70,75,60,105,60,105);
            new Pokemon(310, "Mega Manectric", new Type[]{typeList.get("Electric")},70,75,80,135,80,135);
            new Pokemon(311, "Plusle", new Type[]{typeList.get("Electric")},60,50,40,85,75,95);
            new Pokemon(312, "Minun", new Type[]{typeList.get("Electric")},60,40,50,75,85,95);
            new Pokemon(313, "Volbeat", new Type[]{typeList.get("Bug")},65,73,75,47,85,85);
            new Pokemon(314, "Illumise", new Type[]{typeList.get("Bug")},65,47,75,73,85,85);
            new Pokemon(315, "Roselia", new Type[]{typeList.get("Grass"),typeList.get("Poison")},50,60,45,100,80,65);
            new Pokemon(316, "Gulpin", new Type[]{typeList.get("Poison")},70,43,53,43,53,40);
            new Pokemon(317, "Swalot", new Type[]{typeList.get("Poison")},100,73,83,73,83,55);
            new Pokemon(318, "Carvanha", new Type[]{typeList.get("Water"),typeList.get("Dark")},45,90,20,65,20,65);
            new Pokemon(319, "Sharpedo", new Type[]{typeList.get("Water"),typeList.get("Dark")},70,120,40,95,40,95);
            new Pokemon(319, "Mega Sharpedo", new Type[]{typeList.get("Water"),typeList.get("Dark")},70,140,70,110,65,105);
            new Pokemon(320, "Wailmer", new Type[]{typeList.get("Water")},130,70,35,70,35,60);
            new Pokemon(321, "Wailord", new Type[]{typeList.get("Water")},170,90,45,90,45,60);
            new Pokemon(322, "Numel", new Type[]{typeList.get("Fire"),typeList.get("Ground")},60,60,40,65,45,35);
            new Pokemon(323, "Camerupt", new Type[]{typeList.get("Fire"),typeList.get("Ground")},70,100,70,105,75,40);
            new Pokemon(323, "Mega Camerupt", new Type[]{typeList.get("Fire"),typeList.get("Ground")},70,120,100,145,105,20);
            new Pokemon(324, "Torkoal", new Type[]{typeList.get("Fire")},70,85,140,85,70,20);
            new Pokemon(325, "Spoink", new Type[]{typeList.get("Psychic")},60,25,35,70,80,60);
            new Pokemon(326, "Grumpig", new Type[]{typeList.get("Psychic")},80,45,65,90,110,80);
            new Pokemon(327, "Spinda", new Type[]{typeList.get("Normal")},60,60,60,60,60,60);
            new Pokemon(328, "Trapinch", new Type[]{typeList.get("Ground")},45,100,45,45,45,10);
            new Pokemon(329, "Vibrava", new Type[]{typeList.get("Ground"),typeList.get("Dragon")},50,70,50,50,50,70);
            new Pokemon(330, "Flygon", new Type[]{typeList.get("Ground"),typeList.get("Dragon")},80,100,80,80,80,100);
            new Pokemon(331, "Cacnea", new Type[]{typeList.get("Grass")},50,85,40,85,40,35);
            new Pokemon(332, "Cacturne", new Type[]{typeList.get("Grass"),typeList.get("Dark")},70,115,60,115,60,55);
            new Pokemon(333, "Swablu", new Type[]{typeList.get("Normal"),typeList.get("Flying")},45,40,60,40,75,50);
            new Pokemon(334, "Altaria", new Type[]{typeList.get("Dragon"),typeList.get("Flying")},75,70,90,70,105,80);
            new Pokemon(334, "Mega Altaria", new Type[]{typeList.get("Dragon"),typeList.get("Fairy")},75,110,110,110,105,80);
            new Pokemon(335, "Zangoose", new Type[]{typeList.get("Normal")},73,115,60,60,60,90);
            new Pokemon(336, "Seviper", new Type[]{typeList.get("Poison")},73,100,60,100,60,65);
            new Pokemon(337, "Lunatone", new Type[]{typeList.get("Rock"),typeList.get("Psychic")},90,55,65,95,85,70);
            new Pokemon(338, "Solrock", new Type[]{typeList.get("Rock"),typeList.get("Psychic")},90,95,85,55,65,70);
            new Pokemon(339, "Barboach", new Type[]{typeList.get("Water"),typeList.get("Ground")},50,48,43,46,41,60);
            new Pokemon(340, "Whiscash", new Type[]{typeList.get("Water"),typeList.get("Ground")},110,78,73,76,71,60);
            new Pokemon(341, "Corphish", new Type[]{typeList.get("Water")},43,80,65,50,35,35);
            new Pokemon(342, "Crawdaunt", new Type[]{typeList.get("Water"),typeList.get("Dark")},63,120,85,90,55,55);
            new Pokemon(343, "Baltoy", new Type[]{typeList.get("Ground"),typeList.get("Psychic")},40,40,55,40,70,55);
            new Pokemon(344, "Claydol", new Type[]{typeList.get("Ground"),typeList.get("Psychic")},60,70,105,70,120,75);
            new Pokemon(345, "Lileep", new Type[]{typeList.get("Rock"),typeList.get("Grass")},66,41,77,61,87,23);
            new Pokemon(346, "Cradily", new Type[]{typeList.get("Rock"),typeList.get("Grass")},86,81,97,81,107,43);
            new Pokemon(347, "Anorith", new Type[]{typeList.get("Rock"),typeList.get("Bug")},45,95,50,40,50,75);
            new Pokemon(348, "Armaldo", new Type[]{typeList.get("Rock"),typeList.get("Bug")},75,125,100,70,80,45);
            new Pokemon(349, "Feebas", new Type[]{typeList.get("Water")},20,15,20,10,55,80);
            new Pokemon(350, "Milotic", new Type[]{typeList.get("Water")},95,60,79,100,125,81);
            new Pokemon(351, "Castform", new Type[]{typeList.get("Normal")},70,70,70,70,70,70);
            new Pokemon(352, "Kecleon", new Type[]{typeList.get("Normal")},60,90,70,60,120,40);
            new Pokemon(353, "Shuppet", new Type[]{typeList.get("Ghost")},44,75,35,63,33,45);
            new Pokemon(354, "Banette", new Type[]{typeList.get("Ghost")},64,115,65,83,63,65);
            new Pokemon(354, "Mega Banette", new Type[]{typeList.get("Ghost")},64,165,75,93,83,75);
            new Pokemon(355, "Duskull", new Type[]{typeList.get("Ghost")},20,40,90,30,90,25);
            new Pokemon(356, "Dusclops", new Type[]{typeList.get("Ghost")},40,70,130,60,130,25);
            new Pokemon(357, "Tropius", new Type[]{typeList.get("Grass"),typeList.get("Flying")},99,68,83,72,87,51);
            new Pokemon(358, "Chimecho", new Type[]{typeList.get("Psychic")},75,50,80,95,90,65);
            new Pokemon(359, "Absol", new Type[]{typeList.get("Dark")},65,130,60,75,60,75);
            new Pokemon(359, "Mega Absol", new Type[]{typeList.get("Dark")},65,150,60,115,60,115);
            new Pokemon(360, "Wynaut", new Type[]{typeList.get("Psychic")},95,23,48,23,48,23);
            new Pokemon(361, "Snorunt", new Type[]{typeList.get("Ice")},50,50,50,50,50,50);
            new Pokemon(362, "Glalie", new Type[]{typeList.get("Ice")},80,80,80,80,80,80);
            new Pokemon(362, "Mega Glalie", new Type[]{typeList.get("Ice")},80,120,80,120,80,100);
            new Pokemon(363, "Spheal", new Type[]{typeList.get("Ice"),typeList.get("Water")},70,40,50,55,50,25);
            new Pokemon(364, "Sealeo", new Type[]{typeList.get("Ice"),typeList.get("Water")},90,60,70,75,70,45);
            new Pokemon(365, "Walrein", new Type[]{typeList.get("Ice"),typeList.get("Water")},110,80,90,95,90,65);
            new Pokemon(366, "Clamperl", new Type[]{typeList.get("Water")},35,64,85,74,55,32);
            new Pokemon(367, "Huntail", new Type[]{typeList.get("Water")},55,104,105,94,75,52);
            new Pokemon(368, "Gorebyss", new Type[]{typeList.get("Water")},55,84,105,114,75,52);
            new Pokemon(369, "Relicanth", new Type[]{typeList.get("Water"),typeList.get("Rock")},100,90,130,45,65,55);
            new Pokemon(370, "Luvdisc", new Type[]{typeList.get("Water")},43,30,55,40,65,97);
            new Pokemon(371, "Bagon", new Type[]{typeList.get("Dragon")},45,75,60,40,30,50);
            new Pokemon(372, "Shelgon", new Type[]{typeList.get("Dragon")},65,95,100,60,50,50);
            new Pokemon(373, "Salamence", new Type[]{typeList.get("Dragon"),typeList.get("Flying")},95,135,80,110,80,100);
            new Pokemon(373, "Mega Salamence", new Type[]{typeList.get("Dragon"),typeList.get("Flying")},95,145,130,120,90,120);
            new Pokemon(374, "Beldum", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},40,55,80,35,60,30);
            new Pokemon(375, "Metang", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},60,75,100,55,80,50);
            new Pokemon(376, "Metagross", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},80,135,130,95,90,70);
            new Pokemon(376, "Mega Metagross", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},80,145,150,105,110,110);
            new Pokemon(377, "Regirock", new Type[]{typeList.get("Rock")},80,100,200,50,100,50);
            new Pokemon(378, "Regice", new Type[]{typeList.get("Ice")},80,50,100,100,200,50);
            new Pokemon(379, "Registeel", new Type[]{typeList.get("Steel")},80,75,150,75,150,50);
            new Pokemon(380, "Latias", new Type[]{typeList.get("Dragon"),typeList.get("Psychic")},80,80,90,110,130,110);
            new Pokemon(380, "Mega Latias", new Type[]{typeList.get("Dragon"),typeList.get("Psychic")},80,100,120,140,150,110);
            new Pokemon(381, "Latios", new Type[]{typeList.get("Dragon"),typeList.get("Psychic")},80,90,80,130,110,110);
            new Pokemon(381, "Mega Latios", new Type[]{typeList.get("Dragon"),typeList.get("Psychic")},80,130,100,160,120,110);
            new Pokemon(382, "Kyogre", new Type[]{typeList.get("Water")},100,100,90,150,140,90);
            new Pokemon(382, "Primal Kyogre", new Type[]{typeList.get("Water")},100,150,90,180,160,90);
            new Pokemon(383, "Groudon", new Type[]{typeList.get("Ground")},100,150,140,100,90,90);
            new Pokemon(383, "Primal Groudon", new Type[]{typeList.get("Ground"),typeList.get("Fire")},100,180,160,150,90,90);
            new Pokemon(384, "Rayquaza", new Type[]{typeList.get("Dragon"),typeList.get("Flying")},105,150,90,150,90,95);
            new Pokemon(384, "Mega Rayquaza", new Type[]{typeList.get("Dragon"),typeList.get("Flying")},105,180,100,180,100,115);
            new Pokemon(385, "Jirachi", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},100,100,100,100,100,100);
            new Pokemon(386, "Deoxys Normal Forme", new Type[]{typeList.get("Psychic")},50,150,50,150,50,150);
            new Pokemon(386, "Deoxys Attack Forme", new Type[]{typeList.get("Psychic")},50,180,20,180,20,150);
            new Pokemon(386, "Deoxys Defense Forme", new Type[]{typeList.get("Psychic")},50,70,160,70,160,90);
            new Pokemon(386, "Deoxys Speed Forme", new Type[]{typeList.get("Psychic")},50,95,90,95,90,180);
            new Pokemon(387, "Turtwig", new Type[]{typeList.get("Grass")},55,68,64,45,55,31);
            new Pokemon(388, "Grotle", new Type[]{typeList.get("Grass")},75,89,85,55,65,36);
            new Pokemon(389, "Torterra", new Type[]{typeList.get("Grass"),typeList.get("Ground")},95,109,105,75,85,56);
            new Pokemon(390, "Chimchar", new Type[]{typeList.get("Fire")},44,58,44,58,44,61);
            new Pokemon(391, "Monferno", new Type[]{typeList.get("Fire"),typeList.get("Fighting")},64,78,52,78,52,81);
            new Pokemon(392, "Infernape", new Type[]{typeList.get("Fire"),typeList.get("Fighting")},76,104,71,104,71,108);
            new Pokemon(393, "Piplup", new Type[]{typeList.get("Water")},53,51,53,61,56,40);
            new Pokemon(394, "Prinplup", new Type[]{typeList.get("Water")},64,66,68,81,76,50);
            new Pokemon(395, "Empoleon", new Type[]{typeList.get("Water"),typeList.get("Steel")},84,86,88,111,101,60);
            new Pokemon(396, "Starly", new Type[]{typeList.get("Normal"),typeList.get("Flying")},40,55,30,30,30,60);
            new Pokemon(397, "Staravia", new Type[]{typeList.get("Normal"),typeList.get("Flying")},55,75,50,40,40,80);
            new Pokemon(398, "Staraptor", new Type[]{typeList.get("Normal"),typeList.get("Flying")},85,120,70,50,60,100);
            new Pokemon(399, "Bidoof", new Type[]{typeList.get("Normal")},59,45,40,35,40,31);
            new Pokemon(400, "Bibarel", new Type[]{typeList.get("Normal"),typeList.get("Water")},79,85,60,55,60,71);
            new Pokemon(401, "Kricketot", new Type[]{typeList.get("Bug")},37,25,41,25,41,25);
            new Pokemon(402, "Kricketune", new Type[]{typeList.get("Bug")},77,85,51,55,51,65);
            new Pokemon(403, "Shinx", new Type[]{typeList.get("Electric")},45,65,34,40,34,45);
            new Pokemon(404, "Luxio", new Type[]{typeList.get("Electric")},60,85,49,60,49,60);
            new Pokemon(405, "Luxray", new Type[]{typeList.get("Electric")},80,120,79,95,79,70);
            new Pokemon(406, "Budew", new Type[]{typeList.get("Grass"),typeList.get("Poison")},40,30,35,50,70,55);
            new Pokemon(407, "Roserade", new Type[]{typeList.get("Grass"),typeList.get("Poison")},60,70,65,125,105,90);
            new Pokemon(408, "Cranidos", new Type[]{typeList.get("Rock")},67,125,40,30,30,58);
            new Pokemon(409, "Rampardos", new Type[]{typeList.get("Rock")},97,165,60,65,50,58);
            new Pokemon(410, "Shieldon", new Type[]{typeList.get("Rock"),typeList.get("Steel")},30,42,118,42,88,30);
            new Pokemon(411, "Bastiodon", new Type[]{typeList.get("Rock"),typeList.get("Steel")},60,52,168,47,138,30);
            new Pokemon(412, "Burmy", new Type[]{typeList.get("Bug")},40,29,45,29,45,36);
            new Pokemon(413, "Wormadam Plant Cloak", new Type[]{typeList.get("Bug"),typeList.get("Grass")},60,59,85,79,105,36);
            new Pokemon(413, "Wormadam Sandy Cloak", new Type[]{typeList.get("Bug"),typeList.get("Ground")},60,79,105,59,85,36);
            new Pokemon(413, "Wormadam Trash Cloak", new Type[]{typeList.get("Bug"),typeList.get("Steel")},60,69,95,69,95,36);
            new Pokemon(414, "Mothim", new Type[]{typeList.get("Bug"),typeList.get("Flying")},70,94,50,94,50,66);
            new Pokemon(415, "Combee", new Type[]{typeList.get("Bug"),typeList.get("Flying")},30,30,42,30,42,70);
            new Pokemon(416, "Vespiquen", new Type[]{typeList.get("Bug"),typeList.get("Flying")},70,80,102,80,102,40);
            new Pokemon(417, "Pachirisu", new Type[]{typeList.get("Electric")},60,45,70,45,90,95);
            new Pokemon(418, "Buizel", new Type[]{typeList.get("Water")},55,65,35,60,30,85);
            new Pokemon(419, "Floatzel", new Type[]{typeList.get("Water")},85,105,55,85,50,115);
            new Pokemon(420, "Cherubi", new Type[]{typeList.get("Grass")},45,35,45,62,53,35);
            new Pokemon(421, "Cherrim", new Type[]{typeList.get("Grass")},70,60,70,87,78,85);
            new Pokemon(422, "Shellos", new Type[]{typeList.get("Water")},76,48,48,57,62,34);
            new Pokemon(423, "Gastrodon", new Type[]{typeList.get("Water"),typeList.get("Ground")},111,83,68,92,82,39);
            new Pokemon(424, "Ambipom", new Type[]{typeList.get("Normal")},75,100,66,60,66,115);
            new Pokemon(425, "Drifloon", new Type[]{typeList.get("Ghost"),typeList.get("Flying")},90,50,34,60,44,70);
            new Pokemon(426, "Drifblim", new Type[]{typeList.get("Ghost"),typeList.get("Flying")},150,80,44,90,54,80);
            new Pokemon(427, "Buneary", new Type[]{typeList.get("Normal")},55,66,44,44,56,85);
            new Pokemon(428, "Lopunny", new Type[]{typeList.get("Normal")},65,76,84,54,96,105);
            new Pokemon(428, "Mega Lopunny", new Type[]{typeList.get("Normal"),typeList.get("Fighting")},65,136,94,54,96,135);
            new Pokemon(429, "Mismagius", new Type[]{typeList.get("Ghost")},60,60,60,105,105,105);
            new Pokemon(430, "Honchkrow", new Type[]{typeList.get("Dark"),typeList.get("Flying")},100,125,52,105,52,71);
            new Pokemon(431, "Glameow", new Type[]{typeList.get("Normal")},49,55,42,42,37,85);
            new Pokemon(432, "Purugly", new Type[]{typeList.get("Normal")},71,82,64,64,59,112);
            new Pokemon(433, "Chingling", new Type[]{typeList.get("Psychic")},45,30,50,65,50,45);
            new Pokemon(434, "Stunky", new Type[]{typeList.get("Poison"),typeList.get("Dark")},63,63,47,41,41,74);
            new Pokemon(435, "Skuntank", new Type[]{typeList.get("Poison"),typeList.get("Dark")},103,93,67,71,61,84);
            new Pokemon(436, "Bronzor", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},57,24,86,24,86,23);
            new Pokemon(437, "Bronzong", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},67,89,116,79,116,33);
            new Pokemon(438, "Bonsly", new Type[]{typeList.get("Rock")},50,80,95,10,45,10);
            new Pokemon(439, "Mime Jr.", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},20,25,45,70,90,60);
            new Pokemon(440, "Happiny", new Type[]{typeList.get("Normal")},100,5,5,15,65,30);
            new Pokemon(441, "Chatot", new Type[]{typeList.get("Normal"),typeList.get("Flying")},76,65,45,92,42,91);
            new Pokemon(442, "Spiritomb", new Type[]{typeList.get("Ghost"),typeList.get("Dark")},50,92,108,92,108,35);
            new Pokemon(443, "Gible", new Type[]{typeList.get("Dragon"),typeList.get("Ground")},58,70,45,40,45,42);
            new Pokemon(444, "Gabite", new Type[]{typeList.get("Dragon"),typeList.get("Ground")},68,90,65,50,55,82);
            new Pokemon(445, "Garchomp", new Type[]{typeList.get("Dragon"),typeList.get("Ground")},108,130,95,80,85,102);
            new Pokemon(445, "Mega Garchomp", new Type[]{typeList.get("Dragon"),typeList.get("Ground")},108,170,115,120,95,92);
            new Pokemon(446, "Munchlax", new Type[]{typeList.get("Normal")},135,85,40,40,85,5);
            new Pokemon(447, "Riolu", new Type[]{typeList.get("Fighting")},40,70,40,35,40,60);
            new Pokemon(448, "Lucario", new Type[]{typeList.get("Fighting"),typeList.get("Steel")},70,110,70,115,70,90);
            new Pokemon(448, "Mega Lucario", new Type[]{typeList.get("Fighting"),typeList.get("Steel")},70,145,88,140,70,112);
            new Pokemon(449, "Hippopotas", new Type[]{typeList.get("Ground")},68,72,78,38,42,32);
            new Pokemon(450, "Hippowdon", new Type[]{typeList.get("Ground")},108,112,118,68,72,47);
            new Pokemon(451, "Skorupi", new Type[]{typeList.get("Poison"),typeList.get("Bug")},40,50,90,30,55,65);
            new Pokemon(452, "Drapion", new Type[]{typeList.get("Poison"),typeList.get("Dark")},70,90,110,60,75,95);
            new Pokemon(453, "Croagunk", new Type[]{typeList.get("Poison"),typeList.get("Fighting")},48,61,40,61,40,50);
            new Pokemon(454, "Toxicroak", new Type[]{typeList.get("Poison"),typeList.get("Fighting")},83,106,65,86,65,85);
            new Pokemon(455, "Carnivine", new Type[]{typeList.get("Grass")},74,100,72,90,72,46);
            new Pokemon(456, "Finneon", new Type[]{typeList.get("Water")},49,49,56,49,61,66);
            new Pokemon(457, "Lumineon", new Type[]{typeList.get("Water")},69,69,76,69,86,91);
            new Pokemon(458, "Mantyke", new Type[]{typeList.get("Water"),typeList.get("Flying")},45,20,50,60,120,50);
            new Pokemon(459, "Snover", new Type[]{typeList.get("Grass"),typeList.get("Ice")},60,62,50,62,60,40);
            new Pokemon(460, "Abomasnow", new Type[]{typeList.get("Grass"),typeList.get("Ice")},90,92,75,92,85,60);
            new Pokemon(460, "Mega Abomasnow", new Type[]{typeList.get("Grass"),typeList.get("Ice")},90,132,105,132,105,30);
            new Pokemon(461, "Weavile", new Type[]{typeList.get("Dark"),typeList.get("Ice")},70,120,65,45,85,125);
            new Pokemon(462, "Magnezone", new Type[]{typeList.get("Electric"),typeList.get("Steel")},70,70,115,130,90,60);
            new Pokemon(463, "Lickilicky", new Type[]{typeList.get("Normal")},110,85,95,80,95,50);
            new Pokemon(464, "Rhyperior", new Type[]{typeList.get("Ground"),typeList.get("Rock")},115,140,130,55,55,40);
            new Pokemon(465, "Tangrowth", new Type[]{typeList.get("Grass")},100,100,125,110,50,50);
            new Pokemon(466, "Electivire", new Type[]{typeList.get("Electric")},75,123,67,95,85,95);
            new Pokemon(467, "Magmortar", new Type[]{typeList.get("Fire")},75,95,67,125,95,83);
            new Pokemon(468, "Togekiss", new Type[]{typeList.get("Fairy"),typeList.get("Flying")},85,50,95,120,115,80);
            new Pokemon(469, "Yanmega", new Type[]{typeList.get("Bug"),typeList.get("Flying")},86,76,86,116,56,95);
            new Pokemon(470, "Leafeon", new Type[]{typeList.get("Grass")},65,110,130,60,65,95);
            new Pokemon(471, "Glaceon", new Type[]{typeList.get("Ice")},65,60,110,130,95,65);
            new Pokemon(472, "Gliscor", new Type[]{typeList.get("Ground"),typeList.get("Flying")},75,95,125,45,75,95);
            new Pokemon(473, "Mamoswine", new Type[]{typeList.get("Ice"),typeList.get("Ground")},110,130,80,70,60,80);
            new Pokemon(474, "Porygon-Z", new Type[]{typeList.get("Normal")},85,80,70,135,75,90);
            new Pokemon(475, "Gallade", new Type[]{typeList.get("Psychic"),typeList.get("Fighting")},68,125,65,65,115,80);
            new Pokemon(475, "Mega Gallade", new Type[]{typeList.get("Psychic"),typeList.get("Fighting")},68,165,95,65,115,110);
            new Pokemon(476, "Probopass", new Type[]{typeList.get("Rock"),typeList.get("Steel")},60,55,145,75,150,40);
            new Pokemon(477, "Dusknoir", new Type[]{typeList.get("Ghost")},45,100,135,65,135,45);
            new Pokemon(478, "Froslass", new Type[]{typeList.get("Ice"),typeList.get("Ghost")},70,80,70,80,70,110);
            new Pokemon(479, "Rotom", new Type[]{typeList.get("Electric"),typeList.get("Ghost")},50,50,77,95,77,91);
            new Pokemon(479, "Rotom Heat ", new Type[]{typeList.get("Electric"),typeList.get("Fire")},50,65,107,105,107,86);
            new Pokemon(479, "Rotom Wash ", new Type[]{typeList.get("Electric"),typeList.get("Water")},50,65,107,105,107,86);
            new Pokemon(479, "Rotom Frost ", new Type[]{typeList.get("Electric"),typeList.get("Ice")},50,65,107,105,107,86);
            new Pokemon(479, "Rotom Fan ", new Type[]{typeList.get("Electric"),typeList.get("Flying")},50,65,107,105,107,86);
            new Pokemon(479, "Rotom Mow", new Type[]{typeList.get("Electric"),typeList.get("Grass")},50,65,107,105,107,86);
            new Pokemon(480, "Uxie", new Type[]{typeList.get("Psychic")},75,75,130,75,130,95);
            new Pokemon(481, "Mesprit", new Type[]{typeList.get("Psychic")},80,105,105,105,105,80);
            new Pokemon(482, "Azelf", new Type[]{typeList.get("Psychic")},75,125,70,125,70,115);
            new Pokemon(483, "Dialga", new Type[]{typeList.get("Steel"),typeList.get("Dragon")},100,120,120,150,100,90);
            new Pokemon(483, "Dialga Origin Forme", new Type[]{typeList.get("Steel"),typeList.get("Dragon")},100,100,120,150,120,90);
            new Pokemon(484, "Palkia", new Type[]{typeList.get("Water"),typeList.get("Dragon")},90,120,100,150,120,100);
            new Pokemon(484, "Palkia Origin Forme", new Type[]{typeList.get("Water"),typeList.get("Dragon")},90,100,100,150,120,120);
            new Pokemon(485, "Heatran", new Type[]{typeList.get("Fire"),typeList.get("Steel")},91,90,106,130,106,77);
            new Pokemon(486, "Regigigas", new Type[]{typeList.get("Normal")},110,160,110,80,110,100);
            new Pokemon(487, "Giratina Altered Forme", new Type[]{typeList.get("Ghost"),typeList.get("Dragon")},150,100,120,100,120,90);
            new Pokemon(487, "Giratina Origin Forme", new Type[]{typeList.get("Ghost"),typeList.get("Dragon")},150,120,100,120,100,90);
            new Pokemon(488, "Cresselia", new Type[]{typeList.get("Psychic")},120,70,110,75,120,85);
            new Pokemon(489, "Phione", new Type[]{typeList.get("Water")},80,80,80,80,80,80);
            new Pokemon(490, "Manaphy", new Type[]{typeList.get("Water")},100,100,100,100,100,100);
            new Pokemon(491, "Darkrai", new Type[]{typeList.get("Dark")},70,90,90,135,90,125);
            new Pokemon(492, "Shaymin Land Forme", new Type[]{typeList.get("Grass")},100,100,100,100,100,100);
            new Pokemon(492, "Shaymin Sky Forme", new Type[]{typeList.get("Grass"),typeList.get("Flying")},100,103,75,120,75,127);
            new Pokemon(493, "Arceus", new Type[]{typeList.get("Normal")},120,120,120,120,120,120);
            new Pokemon(494, "Victini", new Type[]{typeList.get("Psychic"),typeList.get("Fire")},100,100,100,100,100,100);
            new Pokemon(495, "Snivy", new Type[]{typeList.get("Grass")},45,45,55,45,55,63);
            new Pokemon(496, "Servine", new Type[]{typeList.get("Grass")},60,60,75,60,75,83);
            new Pokemon(497, "Serperior", new Type[]{typeList.get("Grass")},75,75,95,75,95,113);
            new Pokemon(498, "Tepig", new Type[]{typeList.get("Fire")},65,63,45,45,45,45);
            new Pokemon(499, "Pignite", new Type[]{typeList.get("Fire"),typeList.get("Fighting")},90,93,55,70,55,55);
            new Pokemon(500, "Emboar", new Type[]{typeList.get("Fire"),typeList.get("Fighting")},110,123,65,100,65,65);
            new Pokemon(501, "Oshawott", new Type[]{typeList.get("Water")},55,55,45,63,45,45);
            new Pokemon(502, "Dewott", new Type[]{typeList.get("Water")},75,75,60,83,60,60);
            new Pokemon(503, "Samurott", new Type[]{typeList.get("Water")},95,100,85,108,70,70);
            new Pokemon(503, "Hisuian Samurott", new Type[]{typeList.get("Water"),typeList.get("Dark")},90,108,80,100,65,85);
            new Pokemon(504, "Patrat", new Type[]{typeList.get("Normal")},45,55,39,35,39,42);
            new Pokemon(505, "Watchog", new Type[]{typeList.get("Normal")},60,85,69,60,69,77);
            new Pokemon(506, "Lillipup", new Type[]{typeList.get("Normal")},45,60,45,25,45,55);
            new Pokemon(507, "Herdier", new Type[]{typeList.get("Normal")},65,80,65,35,65,60);
            new Pokemon(508, "Stoutland", new Type[]{typeList.get("Normal")},85,110,90,45,90,80);
            new Pokemon(509, "Purrloin", new Type[]{typeList.get("Dark")},41,50,37,50,37,66);
            new Pokemon(510, "Liepard", new Type[]{typeList.get("Dark")},64,88,50,88,50,106);
            new Pokemon(511, "Pansage", new Type[]{typeList.get("Grass")},50,53,48,53,48,64);
            new Pokemon(512, "Simisage", new Type[]{typeList.get("Grass")},75,98,63,98,63,101);
            new Pokemon(513, "Pansear", new Type[]{typeList.get("Fire")},50,53,48,53,48,64);
            new Pokemon(514, "Simisear", new Type[]{typeList.get("Fire")},75,98,63,98,63,101);
            new Pokemon(515, "Panpour", new Type[]{typeList.get("Water")},50,53,48,53,48,64);
            new Pokemon(516, "Simipour", new Type[]{typeList.get("Water")},75,98,63,98,63,101);
            new Pokemon(517, "Munna", new Type[]{typeList.get("Psychic")},76,25,45,67,55,24);
            new Pokemon(518, "Musharna", new Type[]{typeList.get("Psychic")},116,55,85,107,95,29);
            new Pokemon(519, "Pidove", new Type[]{typeList.get("Normal"),typeList.get("Flying")},50,55,50,36,30,43);
            new Pokemon(520, "Tranquill", new Type[]{typeList.get("Normal"),typeList.get("Flying")},62,77,62,50,42,65);
            new Pokemon(521, "Unfezant", new Type[]{typeList.get("Normal"),typeList.get("Flying")},80,115,80,65,55,93);
            new Pokemon(522, "Blitzle", new Type[]{typeList.get("Electric")},45,60,32,50,32,76);
            new Pokemon(523, "Zebstrika", new Type[]{typeList.get("Electric")},75,100,63,80,63,116);
            new Pokemon(524, "Roggenrola", new Type[]{typeList.get("Rock")},55,75,85,25,25,15);
            new Pokemon(525, "Boldore", new Type[]{typeList.get("Rock")},70,105,105,50,40,20);
            new Pokemon(526, "Gigalith", new Type[]{typeList.get("Rock")},85,135,130,60,80,25);
            new Pokemon(527, "Woobat", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},65,45,43,55,43,72);
            new Pokemon(528, "Swoobat", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},67,57,55,77,55,114);
            new Pokemon(529, "Drilbur", new Type[]{typeList.get("Ground")},60,85,40,30,45,68);
            new Pokemon(530, "Excadrill", new Type[]{typeList.get("Ground"),typeList.get("Steel")},110,135,60,50,65,88);
            new Pokemon(531, "Audino", new Type[]{typeList.get("Normal")},103,60,86,60,86,50);
            new Pokemon(531, "Mega Audino", new Type[]{typeList.get("Normal"),typeList.get("Fairy")},103,60,126,80,126,50);
            new Pokemon(532, "Timburr", new Type[]{typeList.get("Fighting")},75,80,55,25,35,35);
            new Pokemon(533, "Gurdurr", new Type[]{typeList.get("Fighting")},85,105,85,40,50,40);
            new Pokemon(534, "Conkeldurr", new Type[]{typeList.get("Fighting")},105,140,95,55,65,45);
            new Pokemon(535, "Tympole", new Type[]{typeList.get("Water")},50,50,40,50,40,64);
            new Pokemon(536, "Palpitoad", new Type[]{typeList.get("Water"),typeList.get("Ground")},75,65,55,65,55,69);
            new Pokemon(537, "Seismitoad", new Type[]{typeList.get("Water"),typeList.get("Ground")},105,95,75,85,75,74);
            new Pokemon(538, "Throh", new Type[]{typeList.get("Fighting")},120,100,85,30,85,45);
            new Pokemon(539, "Sawk", new Type[]{typeList.get("Fighting")},75,125,75,30,75,85);
            new Pokemon(540, "Sewaddle", new Type[]{typeList.get("Bug"),typeList.get("Grass")},45,53,70,40,60,42);
            new Pokemon(541, "Swadloon", new Type[]{typeList.get("Bug"),typeList.get("Grass")},55,63,90,50,80,42);
            new Pokemon(542, "Leavanny", new Type[]{typeList.get("Bug"),typeList.get("Grass")},75,103,80,70,80,92);
            new Pokemon(543, "Venipede", new Type[]{typeList.get("Bug"),typeList.get("Poison")},30,45,59,30,39,57);
            new Pokemon(544, "Whirlipede", new Type[]{typeList.get("Bug"),typeList.get("Poison")},40,55,99,40,79,47);
            new Pokemon(545, "Scolipede", new Type[]{typeList.get("Bug"),typeList.get("Poison")},60,100,89,55,69,112);
            new Pokemon(546, "Cottonee", new Type[]{typeList.get("Grass"),typeList.get("Fairy")},40,27,60,37,50,66);
            new Pokemon(547, "Whimsicott", new Type[]{typeList.get("Grass"),typeList.get("Fairy")},60,67,85,77,75,116);
            new Pokemon(548, "Petilil", new Type[]{typeList.get("Grass")},45,35,50,70,50,30);
            new Pokemon(549, "Lilligant", new Type[]{typeList.get("Grass")},70,60,75,110,75,90);
            new Pokemon(549, "Hisuian Lilligant", new Type[]{typeList.get("Grass"),typeList.get("Fighting")},70,105,75,50,75,105);
            new Pokemon(550, "Basculin", new Type[]{typeList.get("Water")},70,92,65,80,55,98);
            new Pokemon(551, "Sandile", new Type[]{typeList.get("Ground"),typeList.get("Dark")},50,72,35,35,35,65);
            new Pokemon(552, "Krokorok", new Type[]{typeList.get("Ground"),typeList.get("Dark")},60,82,45,45,45,74);
            new Pokemon(553, "Krookodile", new Type[]{typeList.get("Ground"),typeList.get("Dark")},95,117,80,65,70,92);
            new Pokemon(554, "Darumaka", new Type[]{typeList.get("Fire")},70,90,45,15,45,50);
            new Pokemon(554, "Galarian Darumaka", new Type[]{typeList.get("Ice")},70,90,45,15,45,50);
            new Pokemon(555, "Darmanitan Standard Mode", new Type[]{typeList.get("Fire")},105,140,55,30,55,95);
            new Pokemon(555, "Darmanitan Zen Mode", new Type[]{typeList.get("Fire"),typeList.get("Psychic")},105,30,105,140,105,55);
            new Pokemon(555, "Galarian Darmanitan Standard Mode", new Type[]{typeList.get("Ice")},105,140,55,30,55,95);
            new Pokemon(555, "Galarian Darmanitan Zen Mode", new Type[]{typeList.get("Ice"),typeList.get("Fire")},105,160,55,30,55,135);
            new Pokemon(556, "Maractus", new Type[]{typeList.get("Grass")},75,86,67,106,67,60);
            new Pokemon(557, "Dwebble", new Type[]{typeList.get("Bug"),typeList.get("Rock")},50,65,85,35,35,55);
            new Pokemon(558, "Crustle", new Type[]{typeList.get("Bug"),typeList.get("Rock")},70,105,125,65,75,45);
            new Pokemon(559, "Scraggy", new Type[]{typeList.get("Dark"),typeList.get("Fighting")},50,75,70,35,70,48);
            new Pokemon(560, "Scrafty", new Type[]{typeList.get("Dark"),typeList.get("Fighting")},65,90,115,45,115,58);
            new Pokemon(561, "Sigilyph", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},72,58,80,103,80,97);
            new Pokemon(562, "Yamask", new Type[]{typeList.get("Ghost")},38,30,85,55,65,30);
            new Pokemon(562, "Galarian Yamask", new Type[]{typeList.get("Ground"),typeList.get("Ghost")},38,55,85,30,65,30);
            new Pokemon(563, "Cofagrigus", new Type[]{typeList.get("Ghost")},58,50,145,95,105,30);
            new Pokemon(564, "Tirtouga", new Type[]{typeList.get("Water"),typeList.get("Rock")},54,78,103,53,45,22);
            new Pokemon(565, "Carracosta", new Type[]{typeList.get("Water"),typeList.get("Rock")},74,108,133,83,65,32);
            new Pokemon(566, "Archen", new Type[]{typeList.get("Rock"),typeList.get("Flying")},55,112,45,74,45,70);
            new Pokemon(567, "Archeops", new Type[]{typeList.get("Rock"),typeList.get("Flying")},75,140,65,112,65,110);
            new Pokemon(568, "Trubbish", new Type[]{typeList.get("Poison")},50,50,62,40,62,65);
            new Pokemon(569, "Garbodor", new Type[]{typeList.get("Poison")},80,95,82,60,82,75);
            new Pokemon(570, "Zorua", new Type[]{typeList.get("Dark")},40,65,40,80,40,65);
            new Pokemon(570, "Hisuian Zorua", new Type[]{typeList.get("Normal"),typeList.get("Ghost")},35,60,40,85,40,70);
            new Pokemon(571, "Zoroark", new Type[]{typeList.get("Dark")},60,105,60,120,60,105);
            new Pokemon(571, "Hisuian Zoroark", new Type[]{typeList.get("Normal"),typeList.get("Ghost")},55,100,60,125,60,110);
            new Pokemon(572, "Minccino", new Type[]{typeList.get("Normal")},55,50,40,40,40,75);
            new Pokemon(573, "Cinccino", new Type[]{typeList.get("Normal")},75,95,60,65,60,115);
            new Pokemon(574, "Gothita", new Type[]{typeList.get("Psychic")},45,30,50,55,65,45);
            new Pokemon(575, "Gothorita", new Type[]{typeList.get("Psychic")},60,45,70,75,85,55);
            new Pokemon(576, "Gothitelle", new Type[]{typeList.get("Psychic")},70,55,95,95,110,65);
            new Pokemon(577, "Solosis", new Type[]{typeList.get("Psychic")},45,30,40,105,50,20);
            new Pokemon(578, "Duosion", new Type[]{typeList.get("Psychic")},65,40,50,125,60,30);
            new Pokemon(579, "Reuniclus", new Type[]{typeList.get("Psychic")},110,65,75,125,85,30);
            new Pokemon(580, "Ducklett", new Type[]{typeList.get("Water"),typeList.get("Flying")},62,44,50,44,50,55);
            new Pokemon(581, "Swanna", new Type[]{typeList.get("Water"),typeList.get("Flying")},75,87,63,87,63,98);
            new Pokemon(582, "Vanillite", new Type[]{typeList.get("Ice")},36,50,50,65,60,44);
            new Pokemon(583, "Vanillish", new Type[]{typeList.get("Ice")},51,65,65,80,75,59);
            new Pokemon(584, "Vanilluxe", new Type[]{typeList.get("Ice")},71,95,85,110,95,79);
            new Pokemon(585, "Deerling", new Type[]{typeList.get("Normal"),typeList.get("Grass")},60,60,50,40,50,75);
            new Pokemon(586, "Sawsbuck", new Type[]{typeList.get("Normal"),typeList.get("Grass")},80,100,70,60,70,95);
            new Pokemon(587, "Emolga", new Type[]{typeList.get("Electric"),typeList.get("Flying")},55,75,60,75,60,103);
            new Pokemon(588, "Karrablast", new Type[]{typeList.get("Bug")},50,75,45,40,45,60);
            new Pokemon(589, "Escavalier", new Type[]{typeList.get("Bug"),typeList.get("Steel")},70,135,105,60,105,20);
            new Pokemon(590, "Foongus", new Type[]{typeList.get("Grass"),typeList.get("Poison")},69,55,45,55,55,15);
            new Pokemon(591, "Amoonguss", new Type[]{typeList.get("Grass"),typeList.get("Poison")},114,85,70,85,80,30);
            new Pokemon(592, "Frillish", new Type[]{typeList.get("Water"),typeList.get("Ghost")},55,40,50,65,85,40);
            new Pokemon(593, "Jellicent", new Type[]{typeList.get("Water"),typeList.get("Ghost")},100,60,70,85,105,60);
            new Pokemon(594, "Alomomola", new Type[]{typeList.get("Water")},165,75,80,40,45,65);
            new Pokemon(595, "Joltik", new Type[]{typeList.get("Bug"),typeList.get("Electric")},50,47,50,57,50,65);
            new Pokemon(596, "Galvantula", new Type[]{typeList.get("Bug"),typeList.get("Electric")},70,77,60,97,60,108);
            new Pokemon(597, "Ferroseed", new Type[]{typeList.get("Grass"),typeList.get("Steel")},44,50,91,24,86,10);
            new Pokemon(598, "Ferrothorn", new Type[]{typeList.get("Grass"),typeList.get("Steel")},74,94,131,54,116,20);
            new Pokemon(599, "Klink", new Type[]{typeList.get("Steel")},40,55,70,45,60,30);
            new Pokemon(600, "Klang", new Type[]{typeList.get("Steel")},60,80,95,70,85,50);
            new Pokemon(601, "Klinklang", new Type[]{typeList.get("Steel")},60,100,115,70,85,90);
            new Pokemon(602, "Tynamo", new Type[]{typeList.get("Electric")},35,55,40,45,40,60);
            new Pokemon(603, "Eelektrik", new Type[]{typeList.get("Electric")},65,85,70,75,70,40);
            new Pokemon(604, "Eelektross", new Type[]{typeList.get("Electric")},85,115,80,105,80,50);
            new Pokemon(605, "Elgyem", new Type[]{typeList.get("Psychic")},55,55,55,85,55,30);
            new Pokemon(606, "Beheeyem", new Type[]{typeList.get("Psychic")},75,75,75,125,95,40);
            new Pokemon(607, "Litwick", new Type[]{typeList.get("Ghost"),typeList.get("Fire")},50,30,55,65,55,20);
            new Pokemon(608, "Lampent", new Type[]{typeList.get("Ghost"),typeList.get("Fire")},60,40,60,95,60,55);
            new Pokemon(609, "Chandelure", new Type[]{typeList.get("Ghost"),typeList.get("Fire")},60,55,90,145,90,80);
            new Pokemon(610, "Axew", new Type[]{typeList.get("Dragon")},46,87,60,30,40,57);
            new Pokemon(611, "Fraxure", new Type[]{typeList.get("Dragon")},66,117,70,40,50,67);
            new Pokemon(612, "Haxorus", new Type[]{typeList.get("Dragon")},76,147,90,60,70,97);
            new Pokemon(613, "Cubchoo", new Type[]{typeList.get("Ice")},55,70,40,60,40,40);
            new Pokemon(614, "Beartic", new Type[]{typeList.get("Ice")},95,130,80,70,80,50);
            new Pokemon(615, "Cryogonal", new Type[]{typeList.get("Ice")},80,50,50,95,135,105);
            new Pokemon(616, "Shelmet", new Type[]{typeList.get("Bug")},50,40,85,40,65,25);
            new Pokemon(617, "Accelgor", new Type[]{typeList.get("Bug")},80,70,40,100,60,145);
            new Pokemon(618, "Stunfisk", new Type[]{typeList.get("Ground"),typeList.get("Electric")},109,66,84,81,99,32);
            new Pokemon(618, "Galarian Stunfisk", new Type[]{typeList.get("Ground"),typeList.get("Steel")},109,81,99,66,84,32);
            new Pokemon(619, "Mienfoo", new Type[]{typeList.get("Fighting")},45,85,50,55,50,65);
            new Pokemon(620, "Mienshao", new Type[]{typeList.get("Fighting")},65,125,60,95,60,105);
            new Pokemon(621, "Druddigon", new Type[]{typeList.get("Dragon")},77,120,90,60,90,48);
            new Pokemon(622, "Golett", new Type[]{typeList.get("Ground"),typeList.get("Ghost")},59,74,50,35,50,35);
            new Pokemon(623, "Golurk", new Type[]{typeList.get("Ground"),typeList.get("Ghost")},89,124,80,55,80,55);
            new Pokemon(624, "Pawniard", new Type[]{typeList.get("Dark"),typeList.get("Steel")},45,85,70,40,40,60);
            new Pokemon(625, "Bisharp", new Type[]{typeList.get("Dark"),typeList.get("Steel")},65,125,100,60,70,70);
            new Pokemon(626, "Bouffalant", new Type[]{typeList.get("Normal")},95,110,95,40,95,55);
            new Pokemon(627, "Rufflet", new Type[]{typeList.get("Normal"),typeList.get("Flying")},70,83,50,37,50,60);
            new Pokemon(628, "Braviary", new Type[]{typeList.get("Normal"),typeList.get("Flying")},100,123,75,57,75,80);
            new Pokemon(628, "Hisuian Braviary", new Type[]{typeList.get("Psychic"),typeList.get("Flying")},110,83,70,112,70,65);
            new Pokemon(629, "Vullaby", new Type[]{typeList.get("Dark"),typeList.get("Flying")},70,55,75,45,65,60);
            new Pokemon(630, "Mandibuzz", new Type[]{typeList.get("Dark"),typeList.get("Flying")},110,65,105,55,95,80);
            new Pokemon(631, "Heatmor", new Type[]{typeList.get("Fire")},85,97,66,105,66,65);
            new Pokemon(632, "Durant", new Type[]{typeList.get("Bug"),typeList.get("Steel")},58,109,112,48,48,109);
            new Pokemon(633, "Deino", new Type[]{typeList.get("Dark"),typeList.get("Dragon")},52,65,50,45,50,38);
            new Pokemon(634, "Zweilous", new Type[]{typeList.get("Dark"),typeList.get("Dragon")},72,85,70,65,70,58);
            new Pokemon(635, "Hydreigon", new Type[]{typeList.get("Dark"),typeList.get("Dragon")},92,105,90,125,90,98);
            new Pokemon(636, "Larvesta", new Type[]{typeList.get("Bug"),typeList.get("Fire")},55,85,55,50,55,60);
            new Pokemon(637, "Volcarona", new Type[]{typeList.get("Bug"),typeList.get("Fire")},85,60,65,135,105,100);
            new Pokemon(638, "Cobalion", new Type[]{typeList.get("Steel"),typeList.get("Fighting")},91,90,129,90,72,108);
            new Pokemon(639, "Terrakion", new Type[]{typeList.get("Rock"),typeList.get("Fighting")},91,129,90,72,90,108);
            new Pokemon(640, "Virizion", new Type[]{typeList.get("Grass"),typeList.get("Fighting")},91,90,72,90,129,108);
            new Pokemon(641, "Tornadus Incarnate Forme", new Type[]{typeList.get("Flying")},79,115,70,125,80,111);
            new Pokemon(641, "Tornadus Therian Forme", new Type[]{typeList.get("Flying")},79,100,80,110,90,121);
            new Pokemon(642, "Thundurus Incarnate Forme", new Type[]{typeList.get("Electric"),typeList.get("Flying")},79,115,70,125,80,111);
            new Pokemon(642, "Thundurus Therian Forme", new Type[]{typeList.get("Electric"),typeList.get("Flying")},79,105,70,145,80,101);
            new Pokemon(643, "Reshiram", new Type[]{typeList.get("Dragon"),typeList.get("Fire")},100,120,100,150,120,90);
            new Pokemon(644, "Zekrom", new Type[]{typeList.get("Dragon"),typeList.get("Electric")},100,150,120,120,100,90);
            new Pokemon(645, "Landorus Incarnate Forme", new Type[]{typeList.get("Ground"),typeList.get("Flying")},89,125,90,115,80,101);
            new Pokemon(645, "Landorus Therian Forme", new Type[]{typeList.get("Ground"),typeList.get("Flying")},89,145,90,105,80,91);
            new Pokemon(646, "Kyurem", new Type[]{typeList.get("Dragon"),typeList.get("Ice")},125,130,90,130,90,95);
            new Pokemon(646, "Black Kyurem", new Type[]{typeList.get("Dragon"),typeList.get("Ice")},125,120,90,170,100,95);
            new Pokemon(646, "White Kyurem", new Type[]{typeList.get("Dragon"),typeList.get("Ice")},125,170,100,120,90,95);
            new Pokemon(647, "Keldeo", new Type[]{typeList.get("Water"),typeList.get("Fighting")},91,72,90,129,90,108);
            new Pokemon(648, "Meloetta Aria Forme", new Type[]{typeList.get("Normal"),typeList.get("Psychic")},100,77,77,128,128,90);
            new Pokemon(648, "Meloetta Pirouette Forme", new Type[]{typeList.get("Normal"),typeList.get("Fighting")},100,128,90,77,77,128);
            new Pokemon(649, "Genesect", new Type[]{typeList.get("Bug"),typeList.get("Steel")},71,120,95,120,95,99);
            new Pokemon(650, "Chespin", new Type[]{typeList.get("Grass")},56,61,65,48,45,38);
            new Pokemon(651, "Quilladin", new Type[]{typeList.get("Grass")},61,78,95,56,58,57);
            new Pokemon(652, "Chesnaught", new Type[]{typeList.get("Grass"),typeList.get("Fighting")},88,107,122,74,75,64);
            new Pokemon(653, "Fennekin", new Type[]{typeList.get("Fire")},40,45,40,62,60,60);
            new Pokemon(654, "Braixen", new Type[]{typeList.get("Fire")},59,59,58,90,70,73);
            new Pokemon(655, "Delphox", new Type[]{typeList.get("Fire"),typeList.get("Psychic")},75,69,72,114,100,104);
            new Pokemon(656, "Froakie", new Type[]{typeList.get("Water")},41,56,40,62,44,71);
            new Pokemon(657, "Frogadier", new Type[]{typeList.get("Water")},54,63,52,83,56,97);
            new Pokemon(658, "Greninja", new Type[]{typeList.get("Water"),typeList.get("Dark")},72,95,67,103,71,122);
            new Pokemon(658, "Ash-Greninja", new Type[]{typeList.get("Water"),typeList.get("Dark")},72,145,67,153,71,132);
            new Pokemon(659, "Bunnelby", new Type[]{typeList.get("Normal")},38,36,38,32,36,57);
            new Pokemon(660, "Diggersby", new Type[]{typeList.get("Normal"),typeList.get("Ground")},85,56,77,50,77,78);
            new Pokemon(661, "Fletchling", new Type[]{typeList.get("Normal"),typeList.get("Flying")},45,50,43,40,38,62);
            new Pokemon(662, "Fletchinder", new Type[]{typeList.get("Fire"),typeList.get("Flying")},62,73,55,56,52,84);
            new Pokemon(663, "Talonflame", new Type[]{typeList.get("Fire"),typeList.get("Flying")},78,81,71,74,69,126);
            new Pokemon(664, "Scatterbug", new Type[]{typeList.get("Bug")},38,35,40,27,25,35);
            new Pokemon(665, "Spewpa", new Type[]{typeList.get("Bug")},45,22,60,27,30,29);
            new Pokemon(666, "Vivillon", new Type[]{typeList.get("Bug"),typeList.get("Flying")},80,52,50,90,50,89);
            new Pokemon(667, "Litleo", new Type[]{typeList.get("Fire"),typeList.get("Normal")},62,50,58,73,54,72);
            new Pokemon(668, "Pyroar", new Type[]{typeList.get("Fire"),typeList.get("Normal")},86,68,72,109,66,106);
            new Pokemon(669, "Flabébé", new Type[]{typeList.get("Fairy")},44,38,39,61,79,42);
            new Pokemon(670, "Floette", new Type[]{typeList.get("Fairy")},54,45,47,75,98,52);
            new Pokemon(671, "Florges", new Type[]{typeList.get("Fairy")},78,65,68,112,154,75);
            new Pokemon(672, "Skiddo", new Type[]{typeList.get("Grass")},66,65,48,62,57,52);
            new Pokemon(673, "Gogoat", new Type[]{typeList.get("Grass")},123,100,62,97,81,68);
            new Pokemon(674, "Pancham", new Type[]{typeList.get("Fighting")},67,82,62,46,48,43);
            new Pokemon(675, "Pangoro", new Type[]{typeList.get("Fighting"),typeList.get("Dark")},95,124,78,69,71,58);
            new Pokemon(676, "Furfrou", new Type[]{typeList.get("Normal")},75,80,60,65,90,102);
            new Pokemon(677, "Espurr", new Type[]{typeList.get("Psychic")},62,48,54,63,60,68);
            new Pokemon(678, "Meowstic", new Type[]{typeList.get("Psychic")},74,48,76,83,81,104);
            new Pokemon(679, "Honedge", new Type[]{typeList.get("Steel"),typeList.get("Ghost")},45,80,100,35,37,28);
            new Pokemon(680, "Doublade", new Type[]{typeList.get("Steel"),typeList.get("Ghost")},59,110,150,45,49,35);
            new Pokemon(6811, "Aegislash Shield Forme", new Type[]{typeList.get("Steel"),typeList.get("Ghost")},60,50,140,50,140,60);
            new Pokemon(6812, "Aegislash Blade Forme", new Type[]{typeList.get("Steel"),typeList.get("Ghost")},60,140,50,140,50,60);
            new Pokemon(682, "Spritzee", new Type[]{typeList.get("Fairy")},78,52,60,63,65,23);
            new Pokemon(683, "Aromatisse", new Type[]{typeList.get("Fairy")},101,72,72,99,89,29);
            new Pokemon(684, "Swirlix", new Type[]{typeList.get("Fairy")},62,48,66,59,57,49);
            new Pokemon(685, "Slurpuff", new Type[]{typeList.get("Fairy")},82,80,86,85,75,72);
            new Pokemon(686, "Inkay", new Type[]{typeList.get("Dark"),typeList.get("Psychic")},53,54,53,37,46,45);
            new Pokemon(687, "Malamar", new Type[]{typeList.get("Dark"),typeList.get("Psychic")},86,92,88,68,75,73);
            new Pokemon(688, "Binacle", new Type[]{typeList.get("Rock"),typeList.get("Water")},42,52,67,39,56,50);
            new Pokemon(689, "Barbaracle", new Type[]{typeList.get("Rock"),typeList.get("Water")},72,105,115,54,86,68);
            new Pokemon(690, "Skrelp", new Type[]{typeList.get("Poison"),typeList.get("Water")},50,60,60,60,60,30);
            new Pokemon(691, "Dragalge", new Type[]{typeList.get("Poison"),typeList.get("Dragon")},65,75,90,97,123,44);
            new Pokemon(692, "Clauncher", new Type[]{typeList.get("Water")},50,53,62,58,63,44);
            new Pokemon(693, "Clawitzer", new Type[]{typeList.get("Water")},71,73,88,120,89,59);
            new Pokemon(694, "Helioptile", new Type[]{typeList.get("Electric"),typeList.get("Normal")},44,38,33,61,43,70);
            new Pokemon(695, "Heliolisk", new Type[]{typeList.get("Electric"),typeList.get("Normal")},62,55,52,109,94,109);
            new Pokemon(696, "Tyrunt", new Type[]{typeList.get("Rock"),typeList.get("Dragon")},58,89,77,45,45,48);
            new Pokemon(697, "Tyrantrum", new Type[]{typeList.get("Rock"),typeList.get("Dragon")},82,121,119,69,59,71);
            new Pokemon(698, "Amaura", new Type[]{typeList.get("Rock"),typeList.get("Ice")},77,59,50,67,63,46);
            new Pokemon(699, "Aurorus", new Type[]{typeList.get("Rock"),typeList.get("Ice")},123,77,72,99,92,58);
            new Pokemon(700, "Sylveon", new Type[]{typeList.get("Fairy")},95,65,65,110,130,60);
            new Pokemon(701, "Hawlucha", new Type[]{typeList.get("Fighting"),typeList.get("Flying")},78,92,75,74,63,118);
            new Pokemon(702, "Dedenne", new Type[]{typeList.get("Electric"),typeList.get("Fairy")},67,58,57,81,67,101);
            new Pokemon(703, "Carbink", new Type[]{typeList.get("Rock"),typeList.get("Fairy")},50,50,150,50,150,50);
            new Pokemon(704, "Goomy", new Type[]{typeList.get("Dragon")},45,50,35,55,75,40);
            new Pokemon(705, "Sliggoo", new Type[]{typeList.get("Dragon")},68,75,53,83,113,60);
            new Pokemon(705, "Hisuian Sliggoo", new Type[]{typeList.get("Steel"),typeList.get("Dragon")},58,75,83,83,113,40);
            new Pokemon(706, "Goodra", new Type[]{typeList.get("Dragon")},90,100,70,110,150,80);
            new Pokemon(706, "Hisuian Goodra", new Type[]{typeList.get("Steel"),typeList.get("Dragon")},80,100,100,110,150,60);
            new Pokemon(707, "Klefki", new Type[]{typeList.get("Steel"),typeList.get("Fairy")},57,80,91,80,87,75);
            new Pokemon(708, "Phantump", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},43,70,48,50,60,38);
            new Pokemon(709, "Trevenant", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},85,110,76,65,82,56);
            new Pokemon(710, "Pumpkaboo Small Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},49,66,70,44,55,51);
            new Pokemon(710, "Pumpkaboo Average Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},44,66,70,44,55,56);
            new Pokemon(710, "Pumpkaboo Large Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},54,66,70,44,55,46);
            new Pokemon(710, "Pumpkaboo Super Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},59,66,70,44,55,41);
            new Pokemon(711, "Gourgeist Small Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},65,90,122,58,75,84);
            new Pokemon(711, "Gourgeist Average Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},55,85,122,58,75,99);
            new Pokemon(711, "Gourgeist Large Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},75,95,122,58,75,69);
            new Pokemon(711, "Gourgeist Super Size", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},85,100,122,58,75,54);
            new Pokemon(712, "Bergmite", new Type[]{typeList.get("Ice")},55,69,85,32,35,28);
            new Pokemon(713, "Avalugg", new Type[]{typeList.get("Ice")},95,117,184,44,46,28);
            new Pokemon(713, "Hisuian Avalugg", new Type[]{typeList.get("Ice"),typeList.get("Rock")},95,127,184,34,36,38);
            new Pokemon(714, "Noibat", new Type[]{typeList.get("Flying"),typeList.get("Dragon")},40,30,35,45,40,55);
            new Pokemon(715, "Noivern", new Type[]{typeList.get("Flying"),typeList.get("Dragon")},85,70,80,97,80,123);
            new Pokemon(716, "Xerneas", new Type[]{typeList.get("Fairy")},126,131,95,131,98,99);
            new Pokemon(717, "Yveltal", new Type[]{typeList.get("Dark"),typeList.get("Flying")},126,131,95,131,98,99);
            new Pokemon(718, "Zygarde 50% Forme", new Type[]{typeList.get("Dragon"),typeList.get("Ground")},108,100,121,81,95,95);
            new Pokemon(718, "Zygarde 10% Forme", new Type[]{typeList.get("Dragon"),typeList.get("Ground")},54,100,71,61,85,115);
            new Pokemon(718, "Zygarde Complete Forme", new Type[]{typeList.get("Dragon"),typeList.get("Ground")},216,100,121,91,95,85);
            new Pokemon(719, "Diancie", new Type[]{typeList.get("Rock"),typeList.get("Fairy")},50,100,150,100,150,50);
            new Pokemon(719, "Mega Diancie", new Type[]{typeList.get("Rock"),typeList.get("Fairy")},50,160,110,160,110,110);
            new Pokemon(720, "Hoopa Confined", new Type[]{typeList.get("Psychic"),typeList.get("Ghost")},80,110,60,150,130,70);
            new Pokemon(720, "Hoopa Unbound", new Type[]{typeList.get("Psychic"),typeList.get("Dark")},80,160,60,170,130,80);
            new Pokemon(721, "Volcanion", new Type[]{typeList.get("Fire"),typeList.get("Water")},80,110,120,130,90,70);
            new Pokemon(722, "Rowlet", new Type[]{typeList.get("Grass"),typeList.get("Flying")},68,55,55,50,50,42);
            new Pokemon(723, "Dartrix", new Type[]{typeList.get("Grass"),typeList.get("Flying")},78,75,75,70,70,52);
            new Pokemon(724, "Decidueye", new Type[]{typeList.get("Grass"),typeList.get("Ghost")},78,107,75,100,100,70);
            new Pokemon(724, "Hisuian Decidueye", new Type[]{typeList.get("Grass"),typeList.get("Fighting")},88,112,80,95,95,60);
            new Pokemon(725, "Litten", new Type[]{typeList.get("Fire")},45,65,40,60,40,70);
            new Pokemon(726, "Torracat", new Type[]{typeList.get("Fire")},65,85,50,80,50,90);
            new Pokemon(727, "Incineroar", new Type[]{typeList.get("Fire"),typeList.get("Dark")},95,115,90,80,90,60);
            new Pokemon(728, "Popplio", new Type[]{typeList.get("Water")},50,54,54,66,56,40);
            new Pokemon(729, "Brionne", new Type[]{typeList.get("Water")},60,69,69,91,81,50);
            new Pokemon(730, "Primarina", new Type[]{typeList.get("Water"),typeList.get("Fairy")},80,74,74,126,116,60);
            new Pokemon(731, "Pikipek", new Type[]{typeList.get("Normal"),typeList.get("Flying")},35,75,30,30,30,65);
            new Pokemon(732, "Trumbeak", new Type[]{typeList.get("Normal"),typeList.get("Flying")},55,85,50,40,50,75);
            new Pokemon(733, "Toucannon", new Type[]{typeList.get("Normal"),typeList.get("Flying")},80,120,75,75,75,60);
            new Pokemon(734, "Yungoos", new Type[]{typeList.get("Normal")},48,70,30,30,30,45);
            new Pokemon(735, "Gumshoos", new Type[]{typeList.get("Normal")},88,110,60,55,60,45);
            new Pokemon(736, "Grubbin", new Type[]{typeList.get("Bug")},47,62,45,55,45,46);
            new Pokemon(737, "Charjabug", new Type[]{typeList.get("Bug"),typeList.get("Electric")},57,82,95,55,75,36);
            new Pokemon(738, "Vikavolt", new Type[]{typeList.get("Bug"),typeList.get("Electric")},77,70,90,145,75,43);
            new Pokemon(739, "Crabrawler", new Type[]{typeList.get("Fighting")},47,82,57,42,47,63);
            new Pokemon(740, "Crabominable", new Type[]{typeList.get("Fighting"),typeList.get("Ice")},97,132,77,62,67,43);
            new Pokemon(741, "Oricorio", new Type[]{typeList.get("Fire"),typeList.get("Flying")},75,70,70,98,70,93);
            new Pokemon(742, "Cutiefly", new Type[]{typeList.get("Bug"),typeList.get("Fairy")},40,45,40,55,40,84);
            new Pokemon(743, "Ribombee", new Type[]{typeList.get("Bug"),typeList.get("Fairy")},60,55,60,95,70,124);
            new Pokemon(744, "Rockruff", new Type[]{typeList.get("Rock")},45,65,40,30,40,60);
            new Pokemon(745, "Lycanroc Midday Form", new Type[]{typeList.get("Rock")},75,115,65,55,65,112);
            new Pokemon(745, "Lycanroc Midnight Form", new Type[]{typeList.get("Rock")},85,115,75,55,75,82);
            new Pokemon(745, "Lycanroc Dusk Form", new Type[]{typeList.get("Rock")},75,117,65,55,65,110);
            new Pokemon(746, "Wishiwashi Solo Form", new Type[]{typeList.get("Water")},45,20,20,25,25,40);
            new Pokemon(746, "Wishiwashi School Form", new Type[]{typeList.get("Water")},45,140,130,140,135,30);
            new Pokemon(747, "Mareanie", new Type[]{typeList.get("Poison"),typeList.get("Water")},50,53,62,43,52,45);
            new Pokemon(748, "Toxapex", new Type[]{typeList.get("Poison"),typeList.get("Water")},50,63,152,53,142,35);
            new Pokemon(749, "Mudbray", new Type[]{typeList.get("Ground")},70,100,70,45,55,45);
            new Pokemon(750, "Mudsdale", new Type[]{typeList.get("Ground")},100,125,100,55,85,35);
            new Pokemon(751, "Dewpider", new Type[]{typeList.get("Water"),typeList.get("Bug")},38,40,52,40,72,27);
            new Pokemon(752, "Araquanid", new Type[]{typeList.get("Water"),typeList.get("Bug")},68,70,92,50,132,42);
            new Pokemon(753, "Fomantis", new Type[]{typeList.get("Grass")},40,55,35,50,35,35);
            new Pokemon(754, "Lurantis", new Type[]{typeList.get("Grass")},70,105,90,80,90,45);
            new Pokemon(755, "Morelull", new Type[]{typeList.get("Grass"),typeList.get("Fairy")},40,35,55,65,75,15);
            new Pokemon(756, "Shiinotic", new Type[]{typeList.get("Grass"),typeList.get("Fairy")},60,45,80,90,100,30);
            new Pokemon(757, "Salandit", new Type[]{typeList.get("Poison"),typeList.get("Fire")},48,44,40,71,40,77);
            new Pokemon(758, "Salazzle", new Type[]{typeList.get("Poison"),typeList.get("Fire")},68,64,60,111,60,117);
            new Pokemon(759, "Stufful", new Type[]{typeList.get("Normal"),typeList.get("Fighting")},70,75,50,45,50,50);
            new Pokemon(760, "Bewear", new Type[]{typeList.get("Normal"),typeList.get("Fighting")},120,125,80,55,60,60);
            new Pokemon(761, "Bounsweet", new Type[]{typeList.get("Grass")},42,30,38,30,38,32);
            new Pokemon(762, "Steenee", new Type[]{typeList.get("Grass")},52,40,48,40,48,62);
            new Pokemon(763, "Tsareena", new Type[]{typeList.get("Grass")},72,120,98,50,98,72);
            new Pokemon(764, "Comfey", new Type[]{typeList.get("Fairy")},51,52,90,82,110,100);
            new Pokemon(765, "Oranguru", new Type[]{typeList.get("Normal"),typeList.get("Psychic")},90,60,80,90,110,60);
            new Pokemon(766, "Passimian", new Type[]{typeList.get("Fighting")},100,120,90,40,60,80);
            new Pokemon(767, "Wimpod", new Type[]{typeList.get("Bug"),typeList.get("Water")},25,35,40,20,30,80);
            new Pokemon(768, "Golisopod", new Type[]{typeList.get("Bug"),typeList.get("Water")},75,125,140,60,90,40);
            new Pokemon(769, "Sandygast", new Type[]{typeList.get("Ghost"),typeList.get("Ground")},55,55,80,70,45,15);
            new Pokemon(770, "Palossand", new Type[]{typeList.get("Ghost"),typeList.get("Ground")},85,75,110,100,75,35);
            new Pokemon(771, "Pyukumuku", new Type[]{typeList.get("Water")},55,60,130,30,130,5);
            new Pokemon(772, "Type: Null", new Type[]{typeList.get("Normal")},95,95,95,95,95,59);
            new Pokemon(773, "Silvally", new Type[]{typeList.get("Normal")},95,95,95,95,95,95);
            new Pokemon(774, "Minior Meteor Form", new Type[]{typeList.get("Rock"),typeList.get("Flying")},60,60,100,60,100,60);
            new Pokemon(774, "Minior Core", new Type[]{typeList.get("Rock"),typeList.get("Flying")},60,100,60,100,60,120);
            new Pokemon(775, "Komala", new Type[]{typeList.get("Normal")},65,115,65,75,95,65);
            new Pokemon(776, "Turtonator", new Type[]{typeList.get("Fire"),typeList.get("Dragon")},60,78,135,91,85,36);
            new Pokemon(777, "Togedemaru", new Type[]{typeList.get("Electric"),typeList.get("Steel")},65,98,63,40,73,96);
            new Pokemon(778, "Mimikyu", new Type[]{typeList.get("Ghost"),typeList.get("Fairy")},55,90,80,50,105,96);
            new Pokemon(779, "Bruxish", new Type[]{typeList.get("Water"),typeList.get("Psychic")},68,105,70,70,70,92);
            new Pokemon(780, "Drampa", new Type[]{typeList.get("Normal"),typeList.get("Dragon")},78,60,85,135,91,36);
            new Pokemon(781, "Dhelmise", new Type[]{typeList.get("Ghost"),typeList.get("Grass")},70,131,100,86,90,40);
            new Pokemon(782, "Jangmo-o", new Type[]{typeList.get("Dragon")},45,55,65,45,45,45);
            new Pokemon(783, "Hakamo-o", new Type[]{typeList.get("Dragon"),typeList.get("Fighting")},55,75,90,65,70,65);
            new Pokemon(784, "Kommo-o", new Type[]{typeList.get("Dragon"),typeList.get("Fighting")},75,110,125,100,105,85);
            new Pokemon(785, "Tapu Koko", new Type[]{typeList.get("Electric"),typeList.get("Fairy")},70,115,85,95,75,130);
            new Pokemon(786, "Tapu Lele", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},70,85,75,130,115,95);
            new Pokemon(787, "Tapu Bulu", new Type[]{typeList.get("Grass"),typeList.get("Fairy")},70,130,115,85,95,75);
            new Pokemon(788, "Tapu Fini", new Type[]{typeList.get("Water"),typeList.get("Fairy")},70,75,115,95,130,85);
            new Pokemon(789, "Cosmog", new Type[]{typeList.get("Psychic")},43,29,31,29,31,37);
            new Pokemon(790, "Cosmoem", new Type[]{typeList.get("Psychic")},43,29,131,29,131,37);
            new Pokemon(791, "Solgaleo", new Type[]{typeList.get("Psychic"),typeList.get("Steel")},137,137,107,113,89,97);
            new Pokemon(792, "Lunala", new Type[]{typeList.get("Psychic"),typeList.get("Ghost")},137,113,89,137,107,97);
            new Pokemon(793, "Nihilego", new Type[]{typeList.get("Rock"),typeList.get("Poison")},109,53,47,127,131,103);
            new Pokemon(794, "Buzzwole", new Type[]{typeList.get("Bug"),typeList.get("Fighting")},107,139,139,53,53,79);
            new Pokemon(795, "Pheromosa", new Type[]{typeList.get("Bug"),typeList.get("Fighting")},71,137,37,137,37,151);
            new Pokemon(796, "Xurkitree", new Type[]{typeList.get("Electric")},83,89,71,173,71,83);
            new Pokemon(797, "Celesteela", new Type[]{typeList.get("Steel"),typeList.get("Flying")},97,101,103,107,101,61);
            new Pokemon(798, "Kartana", new Type[]{typeList.get("Grass"),typeList.get("Steel")},59,181,131,59,31,109);
            new Pokemon(799, "Guzzlord", new Type[]{typeList.get("Dark"),typeList.get("Dragon")},223,101,53,97,53,43);
            new Pokemon(800, "Necrozma", new Type[]{typeList.get("Psychic")},97,107,101,127,89,79);
            new Pokemon(800, "Dusk Mane Necrozma", new Type[]{typeList.get("Psychic"),typeList.get("Steel")},97,157,127,113,109,77);
            new Pokemon(800, "Dawn Wings Necrozma", new Type[]{typeList.get("Psychic"),typeList.get("Ghost")},97,113,109,157,127,77);
            new Pokemon(800, "Ultra Necrozma", new Type[]{typeList.get("Psychic"),typeList.get("Dragon")},97,167,97,167,97,129);
            new Pokemon(801, "Magearna", new Type[]{typeList.get("Steel"),typeList.get("Fairy")},80,95,115,130,115,65);
            new Pokemon(802, "Marshadow", new Type[]{typeList.get("Fighting"),typeList.get("Ghost")},90,125,80,90,90,125);
            new Pokemon(803, "Poipole", new Type[]{typeList.get("Poison")},67,73,67,73,67,73);
            new Pokemon(804, "Naganadel", new Type[]{typeList.get("Poison"),typeList.get("Dragon")},73,73,73,127,73,121);
            new Pokemon(805, "Stakataka", new Type[]{typeList.get("Rock"),typeList.get("Steel")},61,131,211,53,101,13);
            new Pokemon(806, "Blacephalon", new Type[]{typeList.get("Fire"),typeList.get("Ghost")},53,127,53,151,79,107);
            new Pokemon(807, "Zeraora", new Type[]{typeList.get("Electric")},88,112,75,102,80,143);
            new Pokemon(808, "Meltan", new Type[]{typeList.get("Steel")},46,65,65,55,35,34);
            new Pokemon(809, "Melmetal", new Type[]{typeList.get("Steel")},135,143,143,80,65,34);
            new Pokemon(810, "Grookey", new Type[]{typeList.get("Grass")},50,65,50,40,40,65);
            new Pokemon(811, "Thwackey", new Type[]{typeList.get("Grass")},70,85,70,55,60,80);
            new Pokemon(812, "Rillaboom", new Type[]{typeList.get("Grass")},100,125,90,60,70,85);
            new Pokemon(813, "Scorbunny", new Type[]{typeList.get("Fire")},50,71,40,40,40,69);
            new Pokemon(814, "Raboot", new Type[]{typeList.get("Fire")},65,86,60,55,60,94);
            new Pokemon(815, "Cinderace", new Type[]{typeList.get("Fire")},80,116,75,65,75,119);
            new Pokemon(816, "Sobble", new Type[]{typeList.get("Water")},50,40,40,70,40,70);
            new Pokemon(817, "Drizzile", new Type[]{typeList.get("Water")},65,60,55,95,55,90);
            new Pokemon(818, "Inteleon", new Type[]{typeList.get("Water")},70,85,65,125,65,120);
            new Pokemon(819, "Skwovet", new Type[]{typeList.get("Normal")},70,55,55,35,35,25);
            new Pokemon(820, "Greedent", new Type[]{typeList.get("Normal")},120,95,95,55,75,20);
            new Pokemon(821, "Rookidee", new Type[]{typeList.get("Flying")},38,47,35,33,35,57);
            new Pokemon(822, "Corvisquire", new Type[]{typeList.get("Flying")},68,67,55,43,55,77);
            new Pokemon(823, "Corviknight", new Type[]{typeList.get("Flying"),typeList.get("Steel")},98,87,105,53,85,67);
            new Pokemon(824, "Blipbug", new Type[]{typeList.get("Bug")},25,20,20,25,45,45);
            new Pokemon(825, "Dottler", new Type[]{typeList.get("Bug"),typeList.get("Psychic")},50,35,80,50,90,30);
            new Pokemon(826, "Orbeetle", new Type[]{typeList.get("Bug"),typeList.get("Psychic")},60,45,110,80,120,90);
            new Pokemon(827, "Nickit", new Type[]{typeList.get("Dark")},40,28,28,47,52,50);
            new Pokemon(828, "Thievul", new Type[]{typeList.get("Dark")},70,58,58,87,92,90);
            new Pokemon(829, "Gossifleur", new Type[]{typeList.get("Grass")},40,40,60,40,60,10);
            new Pokemon(830, "Eldegoss", new Type[]{typeList.get("Grass")},60,50,90,80,120,60);
            new Pokemon(831, "Wooloo", new Type[]{typeList.get("Normal")},42,40,55,40,45,48);
            new Pokemon(832, "Dubwool", new Type[]{typeList.get("Normal")},72,80,100,60,90,88);
            new Pokemon(833, "Chewtle", new Type[]{typeList.get("Water")},50,64,50,38,38,44);
            new Pokemon(834, "Drednaw", new Type[]{typeList.get("Water"),typeList.get("Rock")},90,115,90,48,68,74);
            new Pokemon(835, "Yamper", new Type[]{typeList.get("Electric")},59,45,50,40,50,26);
            new Pokemon(836, "Boltund", new Type[]{typeList.get("Electric")},69,90,60,90,60,121);
            new Pokemon(837, "Rolycoly", new Type[]{typeList.get("Rock")},30,40,50,40,50,30);
            new Pokemon(838, "Carkol", new Type[]{typeList.get("Rock"),typeList.get("Fire")},80,60,90,60,70,50);
            new Pokemon(839, "Coalossal", new Type[]{typeList.get("Rock"),typeList.get("Fire")},110,80,120,80,90,30);
            new Pokemon(840, "Applin", new Type[]{typeList.get("Grass"),typeList.get("Dragon")},40,40,80,40,40,20);
            new Pokemon(841, "Flapple", new Type[]{typeList.get("Grass"),typeList.get("Dragon")},70,110,80,95,60,70);
            new Pokemon(842, "Appletun", new Type[]{typeList.get("Grass"),typeList.get("Dragon")},110,85,80,100,80,30);
            new Pokemon(843, "Silicobra", new Type[]{typeList.get("Ground")},52,57,75,35,50,46);
            new Pokemon(844, "Sandaconda", new Type[]{typeList.get("Ground")},72,107,125,65,70,71);
            new Pokemon(845, "Cramorant", new Type[]{typeList.get("Flying"),typeList.get("Water")},70,85,55,85,95,85);
            new Pokemon(846, "Arrokuda", new Type[]{typeList.get("Water")},41,63,40,40,30,66);
            new Pokemon(847, "Barraskewda", new Type[]{typeList.get("Water")},61,123,60,60,50,136);
            new Pokemon(848, "Toxel", new Type[]{typeList.get("Electric"),typeList.get("Poison")},40,38,35,54,35,40);
            new Pokemon(849, "Toxtricity", new Type[]{typeList.get("Electric"),typeList.get("Poison")},75,98,70,114,70,75);
            new Pokemon(850, "Sizzlipede", new Type[]{typeList.get("Fire"),typeList.get("Bug")},50,65,45,50,50,45);
            new Pokemon(851, "Centiskorch", new Type[]{typeList.get("Fire"),typeList.get("Bug")},100,115,65,90,90,65);
            new Pokemon(852, "Clobbopus", new Type[]{typeList.get("Fighting")},50,68,60,50,50,32);
            new Pokemon(853, "Grapploct", new Type[]{typeList.get("Fighting")},80,118,90,70,80,42);
            new Pokemon(854, "Sinistea", new Type[]{typeList.get("Ghost")},40,45,45,74,54,50);
            new Pokemon(855, "Polteageist", new Type[]{typeList.get("Ghost")},60,65,65,134,114,70);
            new Pokemon(856, "Hatenna", new Type[]{typeList.get("Psychic")},42,30,45,56,53,39);
            new Pokemon(857, "Hattrem", new Type[]{typeList.get("Psychic")},57,40,65,86,73,49);
            new Pokemon(858, "Hatterene", new Type[]{typeList.get("Psychic"),typeList.get("Fairy")},57,90,95,136,103,29);
            new Pokemon(859, "Impidimp", new Type[]{typeList.get("Dark"),typeList.get("Fairy")},45,45,30,55,40,50);
            new Pokemon(860, "Morgrem", new Type[]{typeList.get("Dark"),typeList.get("Fairy")},65,60,45,75,55,70);
            new Pokemon(861, "Grimmsnarl", new Type[]{typeList.get("Dark"),typeList.get("Fairy")},95,120,65,95,75,60);
            new Pokemon(862, "Obstagoon", new Type[]{typeList.get("Dark"),typeList.get("Normal")},93,90,101,60,81,95);
            new Pokemon(863, "Perrserker", new Type[]{typeList.get("Steel")},70,110,100,50,60,50);
            new Pokemon(864, "Cursola", new Type[]{typeList.get("Ghost")},60,95,50,145,130,30);
            new Pokemon(865, "Sirfetch'd", new Type[]{typeList.get("Fighting")},62,135,95,68,82,65);
            new Pokemon(866, "Mr. Rime", new Type[]{typeList.get("Ice"),typeList.get("Psychic")},80,85,75,110,100,70);
            new Pokemon(867, "Runerigus", new Type[]{typeList.get("Ground"),typeList.get("Ghost")},58,95,145,50,105,30);
            new Pokemon(868, "Milcery", new Type[]{typeList.get("Fairy")},45,40,40,50,61,34);
            new Pokemon(869, "Alcremie", new Type[]{typeList.get("Fairy")},65,60,75,110,121,64);
            new Pokemon(870, "Falinks", new Type[]{typeList.get("Fighting")},65,100,100,70,60,75);
            new Pokemon(871, "Pincurchin", new Type[]{typeList.get("Electric")},48,101,95,91,85,15);
            new Pokemon(872, "Snom", new Type[]{typeList.get("Ice"),typeList.get("Bug")},30,25,35,45,30,20);
            new Pokemon(873, "Frosmoth", new Type[]{typeList.get("Ice"),typeList.get("Bug")},70,65,60,125,90,65);
            new Pokemon(874, "Stonjourner", new Type[]{typeList.get("Rock")},100,125,135,20,20,70);
            new Pokemon(875, "Eiscue Ice Face", new Type[]{typeList.get("Ice")},75,80,110,65,90,50);
            new Pokemon(875, "Eiscue Noice Face", new Type[]{typeList.get("Ice")},75,80,70,65,50,130);
            new Pokemon(876, "Indeedee Male", new Type[]{typeList.get("Psychic"),typeList.get("Normal")},60,65,55,105,95,95);
            new Pokemon(876, "Indeedee Female", new Type[]{typeList.get("Psychic"),typeList.get("Normal")},70,55,65,95,105,85);
            new Pokemon(877, "Morpeko", new Type[]{typeList.get("Electric"),typeList.get("Dark")},58,95,58,70,58,97);
            new Pokemon(878, "Cufant", new Type[]{typeList.get("Steel")},72,80,49,40,49,40);
            new Pokemon(879, "Copperajah", new Type[]{typeList.get("Steel")},122,130,69,80,69,30);
            new Pokemon(880, "Dracozolt", new Type[]{typeList.get("Electric"),typeList.get("Dragon")},90,100,90,80,70,75);
            new Pokemon(881, "Arctozolt", new Type[]{typeList.get("Electric"),typeList.get("Ice")},90,100,90,90,80,55);
            new Pokemon(882, "Dracovish", new Type[]{typeList.get("Water"),typeList.get("Dragon")},90,90,100,70,80,75);
            new Pokemon(883, "Arctovish", new Type[]{typeList.get("Water"),typeList.get("Ice")},90,90,100,80,90,55);
            new Pokemon(884, "Duraludon", new Type[]{typeList.get("Steel"),typeList.get("Dragon")},70,95,115,120,50,85);
            new Pokemon(885, "Dreepy", new Type[]{typeList.get("Dragon"),typeList.get("Ghost")},28,60,30,40,30,82);
            new Pokemon(886, "Drakloak", new Type[]{typeList.get("Dragon"),typeList.get("Ghost")},68,80,50,60,50,102);
            new Pokemon(887, "Dragapult", new Type[]{typeList.get("Dragon"),typeList.get("Ghost")},88,120,75,100,75,142);
            new Pokemon(888, "Zacian Hero of Many Battles", new Type[]{typeList.get("Fairy")},92,120,115,80,115,138);
            new Pokemon(888, "Zacian Crowned Sword", new Type[]{typeList.get("Fairy"),typeList.get("Steel")},92,150,115,80,115,148);
            new Pokemon(889, "Zamazenta Hero of Many Battles", new Type[]{typeList.get("Fighting")},92,120,115,80,115,138);
            new Pokemon(889, "Zamazenta Crowned Shield", new Type[]{typeList.get("Fighting"),typeList.get("Steel")},92,120,140,80,140,128);
            new Pokemon(890, "Eternatus", new Type[]{typeList.get("Poison"),typeList.get("Dragon")},140,85,95,145,95,130);
            new Pokemon(891, "Kubfu", new Type[]{typeList.get("Fighting")},60,90,60,53,50,72);
            new Pokemon(892, "Urshifu", new Type[]{typeList.get("Fighting"),typeList.get("Dark")},100,130,100,63,60,97);
            new Pokemon(893, "Zarude", new Type[]{typeList.get("Dark"),typeList.get("Grass")},105,120,105,70,95,105);
            new Pokemon(894, "Regieleki", new Type[]{typeList.get("Electric")},80,100,50,100,50,200);
            new Pokemon(895, "Regidrago", new Type[]{typeList.get("Dragon")},200,100,50,100,50,80);
            new Pokemon(896, "Glastrier", new Type[]{typeList.get("Ice")},100,145,130,65,110,30);
            new Pokemon(897, "Spectrier", new Type[]{typeList.get("Ghost")},100,65,60,145,80,130);
            new Pokemon(898, "Calyrex", new Type[]{typeList.get("Psychic"),typeList.get("Grass")},100,80,80,80,80,80);
            new Pokemon(898, "Ice Rider Calyrex", new Type[]{typeList.get("Psychic"),typeList.get("Ice")},100,165,150,85,130,50);
            new Pokemon(898, "Shadow Rider Calyrex", new Type[]{typeList.get("Psychic"),typeList.get("Ghost")},100,85,80,165,100,150);
            new Pokemon(899, "Wyrdeer", new Type[]{typeList.get("Normal"),typeList.get("Psychic")},103,105,72,105,75,65);
            new Pokemon(900, "Kleavor", new Type[]{typeList.get("Bug"),typeList.get("Rock")},70,135,95,45,70,85);
            new Pokemon(901, "Ursaluna", new Type[]{typeList.get("Ground"),typeList.get("Normal")},130,140,105,45,80,50);
            new Pokemon(901, "Bloodmoon Ursaluna", new Type[]{typeList.get("Ground"),typeList.get("Normal")},113,70,120,135,65,52);
            new Pokemon(902, "Basculegion Male", new Type[]{typeList.get("Water"),typeList.get("Ghost")},120,112,65,80,75,78);
            new Pokemon(902, "Basculegion Female", new Type[]{typeList.get("Water"),typeList.get("Ghost")},120,92,65,100,75,78);
            new Pokemon(903, "Sneasler", new Type[]{typeList.get("Fighting"),typeList.get("Poison")},80,130,60,40,80,120);
            new Pokemon(904, "Overqwil", new Type[]{typeList.get("Dark"),typeList.get("Poison")},85,115,95,65,65,85);
            new Pokemon(905, "Enamorus Incarnate Forme", new Type[]{typeList.get("Fairy"),typeList.get("Flying")},74,115,70,135,80,106);
            new Pokemon(905, "Enamorus Therian Forme", new Type[]{typeList.get("Fairy"),typeList.get("Flying")},74,115,110,135,100,46);
            new Pokemon(906, "Sprigatito", new Type[]{typeList.get("Grass")},40,61,54,45,45,65);
            new Pokemon(907, "Floragato", new Type[]{typeList.get("Grass")},61,80,63,60,63,83);
            new Pokemon(908, "Meowscarada", new Type[]{typeList.get("Grass"),typeList.get("Dark")},76,110,70,81,70,123);
            new Pokemon(909, "Fuecoco", new Type[]{typeList.get("Fire")},67,45,59,63,40,36);
            new Pokemon(910, "Crocalor", new Type[]{typeList.get("Fire")},81,55,78,90,58,49);
            new Pokemon(911, "Skeledirge", new Type[]{typeList.get("Fire"),typeList.get("Ghost")},104,75,100,110,75,66);
            new Pokemon(912, "Quaxly", new Type[]{typeList.get("Water")},55,65,45,50,45,50);
            new Pokemon(913, "Quaxwell", new Type[]{typeList.get("Water")},70,85,65,65,60,65);
            new Pokemon(914, "Quaquaval", new Type[]{typeList.get("Water"),typeList.get("Fighting")},85,120,80,85,75,85);
            new Pokemon(915, "Lechonk", new Type[]{typeList.get("Normal")},54,45,40,35,45,35);
            new Pokemon(916, "Oinkologne", new Type[]{typeList.get("Normal")},110,100,75,59,80,65);
            new Pokemon(917, "Tarountula", new Type[]{typeList.get("Bug")},35,41,45,29,40,20);
            new Pokemon(918, "Spidops", new Type[]{typeList.get("Bug")},60,79,92,52,86,35);
            new Pokemon(919, "Nymble", new Type[]{typeList.get("Bug")},33,46,40,21,25,45);
            new Pokemon(920, "Lokix", new Type[]{typeList.get("Bug"),typeList.get("Dark")},71,102,78,52,55,92);
            new Pokemon(921, "Pawmi", new Type[]{typeList.get("Electric")},45,50,20,40,25,60);
            new Pokemon(922, "Pawmo", new Type[]{typeList.get("Electric"),typeList.get("Fighting")},60,75,40,50,40,85);
            new Pokemon(923, "Pawmot", new Type[]{typeList.get("Electric"),typeList.get("Fighting")},70,115,70,70,60,105);
            new Pokemon(924, "Tandemaus", new Type[]{typeList.get("Normal")},50,50,45,40,45,75);
            new Pokemon(925, "Maushold", new Type[]{typeList.get("Normal")},74,75,70,65,75,111);
            new Pokemon(926, "Fidough", new Type[]{typeList.get("Fairy")},37,55,70,30,55,65);
            new Pokemon(927, "Dachsbun", new Type[]{typeList.get("Fairy")},57,80,115,50,80,95);
            new Pokemon(928, "Smoliv", new Type[]{typeList.get("Grass"),typeList.get("Normal")},41,35,45,58,51,30);
            new Pokemon(929, "Dolliv", new Type[]{typeList.get("Grass"),typeList.get("Normal")},52,53,60,78,78,33);
            new Pokemon(930, "Arboliva", new Type[]{typeList.get("Grass"),typeList.get("Normal")},78,69,90,125,109,39);
            new Pokemon(931, "Squawkabilly", new Type[]{typeList.get("Normal"),typeList.get("Flying")},82,96,51,45,51,92);
            new Pokemon(932, "Nacli", new Type[]{typeList.get("Rock")},55,55,75,35,35,25);
            new Pokemon(933, "Naclstack", new Type[]{typeList.get("Rock")},60,60,100,35,65,35);
            new Pokemon(934, "Garganacl", new Type[]{typeList.get("Rock")},100,100,130,45,90,35);
            new Pokemon(935, "Charcadet", new Type[]{typeList.get("Fire")},40,50,40,50,40,35);
            new Pokemon(936, "Armarouge", new Type[]{typeList.get("Fire"),typeList.get("Psychic")},85,60,100,125,80,75);
            new Pokemon(937, "Ceruledge", new Type[]{typeList.get("Fire"),typeList.get("Ghost")},75,125,80,60,100,85);
            new Pokemon(938, "Tadbulb", new Type[]{typeList.get("Electric")},61,31,41,59,35,45);
            new Pokemon(939, "Bellibolt", new Type[]{typeList.get("Electric")},109,64,91,103,83,45);
            new Pokemon(940, "Wattrel", new Type[]{typeList.get("Electric"),typeList.get("Flying")},40,40,35,55,40,70);
            new Pokemon(941, "Kilowattrel", new Type[]{typeList.get("Electric"),typeList.get("Flying")},70,70,60,105,60,125);
            new Pokemon(942, "Maschiff", new Type[]{typeList.get("Dark")},60,78,60,40,51,51);
            new Pokemon(943, "Mabosstiff", new Type[]{typeList.get("Dark")},80,120,90,60,70,85);
            new Pokemon(944, "Shroodle", new Type[]{typeList.get("Poison"),typeList.get("Normal")},40,65,35,40,35,75);
            new Pokemon(945, "Grafaiai", new Type[]{typeList.get("Poison"),typeList.get("Normal")},63,95,65,80,72,110);
            new Pokemon(946, "Bramblin", new Type[]{typeList.get("Grass"),typeList.get("Ghost")},40,65,30,45,35,60);
            new Pokemon(947, "Brambleghast", new Type[]{typeList.get("Grass"),typeList.get("Ghost")},55,115,70,80,70,90);
            new Pokemon(948, "Toedscool", new Type[]{typeList.get("Ground"),typeList.get("Grass")},40,40,35,50,100,70);
            new Pokemon(949, "Toedscruel", new Type[]{typeList.get("Ground"),typeList.get("Grass")},80,70,65,80,120,100);
            new Pokemon(950, "Klawf", new Type[]{typeList.get("Rock")},70,100,115,35,55,75);
            new Pokemon(951, "Capsakid", new Type[]{typeList.get("Grass")},50,62,40,62,40,50);
            new Pokemon(952, "Scovillain", new Type[]{typeList.get("Grass"),typeList.get("Fire")},65,108,65,108,65,75);
            new Pokemon(953, "Rellor", new Type[]{typeList.get("Bug")},41,50,60,31,58,30);
            new Pokemon(954, "Rabsca", new Type[]{typeList.get("Bug"),typeList.get("Psychic")},75,50,85,115,100,45);
            new Pokemon(955, "Flittle", new Type[]{typeList.get("Psychic")},30,35,30,55,30,75);
            new Pokemon(956, "Espathra", new Type[]{typeList.get("Psychic")},95,60,60,101,60,105);
            new Pokemon(957, "Tinkatink", new Type[]{typeList.get("Fairy"),typeList.get("Steel")},50,45,45,35,64,58);
            new Pokemon(958, "Tinkatuff", new Type[]{typeList.get("Fairy"),typeList.get("Steel")},65,55,55,45,82,78);
            new Pokemon(959, "Tinkaton", new Type[]{typeList.get("Fairy"),typeList.get("Steel")},85,75,77,70,105,94);
            new Pokemon(960, "Wiglett", new Type[]{typeList.get("Water")},10,55,25,35,25,95);
            new Pokemon(961, "Wugtrio", new Type[]{typeList.get("Water")},35,100,50,50,70,120);
            new Pokemon(962, "Bombirdier", new Type[]{typeList.get("Flying"),typeList.get("Dark")},70,103,85,60,85,82);
            new Pokemon(963, "Finizen", new Type[]{typeList.get("Water")},70,45,40,45,40,75);
            new Pokemon(964, "Palafin Zero", new Type[]{typeList.get("Water")},100,70,72,53,62,100);
            new Pokemon(964, "Palafin Hero", new Type[]{typeList.get("Water")},100,160,97,106,87,100);
            new Pokemon(965, "Varoom", new Type[]{typeList.get("Steel"),typeList.get("Poison")},45,70,63,30,45,47);
            new Pokemon(966, "Revavroom", new Type[]{typeList.get("Steel"),typeList.get("Poison")},80,119,90,54,67,90);
            new Pokemon(967, "Cyclizar", new Type[]{typeList.get("Dragon"),typeList.get("Normal")},70,95,65,85,65,121);
            new Pokemon(968, "Orthworm", new Type[]{typeList.get("Steel")},70,85,145,60,55,65);
            new Pokemon(969, "Glimmet", new Type[]{typeList.get("Rock"),typeList.get("Poison")},48,35,42,105,60,60);
            new Pokemon(970, "Glimmora", new Type[]{typeList.get("Rock"),typeList.get("Poison")},83,55,90,130,81,86);
            new Pokemon(971, "Greavard", new Type[]{typeList.get("Ghost")},50,61,60,30,55,34);
            new Pokemon(972, "Houndstone", new Type[]{typeList.get("Ghost")},72,101,100,50,97,68);
            new Pokemon(973, "Flamigo", new Type[]{typeList.get("Flying"),typeList.get("Fighting")},82,115,74,75,64,90);
            new Pokemon(974, "Cetoddle", new Type[]{typeList.get("Ice")},108,68,45,30,40,43);
            new Pokemon(975, "Cetitan", new Type[]{typeList.get("Ice")},170,113,65,45,55,73);
            new Pokemon(976, "Veluza", new Type[]{typeList.get("Water"),typeList.get("Psychic")},90,102,73,78,65,70);
            new Pokemon(977, "Dondozo", new Type[]{typeList.get("Water")},150,100,115,65,65,35);
            new Pokemon(978, "Tatsugiri", new Type[]{typeList.get("Dragon"),typeList.get("Water")},68,50,60,120,95,82);
            new Pokemon(979, "Annihilape", new Type[]{typeList.get("Fighting"),typeList.get("Ghost")},110,115,80,50,90,90);
            new Pokemon(980, "Clodsire", new Type[]{typeList.get("Poison"),typeList.get("Ground")},130,75,60,45,100,20);
            new Pokemon(981, "Farigiraf", new Type[]{typeList.get("Normal"),typeList.get("Psychic")},120,90,70,110,70,60);
            new Pokemon(982, "Dudunsparce", new Type[]{typeList.get("Normal")},125,100,80,85,75,55);
            new Pokemon(983, "Kingambit", new Type[]{typeList.get("Dark"),typeList.get("Steel")},100,135,120,60,85,50);
            new Pokemon(984, "Great Tusk", new Type[]{typeList.get("Ground"),typeList.get("Fighting")},115,131,131,53,53,87);
            new Pokemon(985, "Scream Tail", new Type[]{typeList.get("Fairy"),typeList.get("Psychic")},115,65,99,65,115,111);
            new Pokemon(986, "Brute Bonnet", new Type[]{typeList.get("Grass"),typeList.get("Dark")},111,127,99,79,99,55);
            new Pokemon(987, "Flutter Mane", new Type[]{typeList.get("Ghost"),typeList.get("Fairy")},55,55,55,135,135,135);
            new Pokemon(988, "Slither Wing", new Type[]{typeList.get("Bug"),typeList.get("Fighting")},85,135,79,85,105,81);
            new Pokemon(989, "Sandy Shocks", new Type[]{typeList.get("Electric"),typeList.get("Ground")},85,81,97,121,85,101);
            new Pokemon(990, "Iron Treads", new Type[]{typeList.get("Ground"),typeList.get("Steel")},90,112,120,72,70,106);
            new Pokemon(991, "Iron Bundle", new Type[]{typeList.get("Ice"),typeList.get("Water")},56,80,114,124,60,136);
            new Pokemon(992, "Iron Hands", new Type[]{typeList.get("Fighting"),typeList.get("Electric")},154,140,108,50,68,50);
            new Pokemon(993, "Iron Jugulis", new Type[]{typeList.get("Dark"),typeList.get("Flying")},94,80,86,122,80,108);
            new Pokemon(994, "Iron Moth", new Type[]{typeList.get("Fire"),typeList.get("Poison")},80,70,60,140,110,110);
            new Pokemon(995, "Iron Thorns", new Type[]{typeList.get("Rock"),typeList.get("Electric")},100,134,110,70,84,72);
            new Pokemon(996, "Frigibax", new Type[]{typeList.get("Dragon"),typeList.get("Ice")},65,75,45,35,45,55);
            new Pokemon(997, "Arctibax", new Type[]{typeList.get("Dragon"),typeList.get("Ice")},90,95,66,45,65,62);
            new Pokemon(998, "Baxcalibur", new Type[]{typeList.get("Dragon"),typeList.get("Ice")},115,145,92,75,86,87);
            new Pokemon(999, "Gimmighoul", new Type[]{typeList.get("Ghost")},45,30,70,75,70,10);
            new Pokemon(1000, "Gholdengo", new Type[]{typeList.get("Steel"),typeList.get("Ghost")},87,60,95,133,91,84);
            new Pokemon(1001, "Wo-Chien", new Type[]{typeList.get("Dark"),typeList.get("Grass")},85,85,100,95,135,70);
            new Pokemon(1002, "Chien-Pao", new Type[]{typeList.get("Dark"),typeList.get("Ice")},80,120,80,90,65,135);
            new Pokemon(1003, "Ting-Lu", new Type[]{typeList.get("Dark"),typeList.get("Ground")},155,110,125,55,80,45);
            new Pokemon(1004, "Chi-Yu", new Type[]{typeList.get("Dark"),typeList.get("Fire")},55,80,80,135,120,100);
            new Pokemon(1005, "Roaring Moon", new Type[]{typeList.get("Dragon"),typeList.get("Dark")},105,139,71,55,101,119);
            new Pokemon(1006, "Iron Valiant", new Type[]{typeList.get("Fairy"),typeList.get("Fighting")},74,130,90,120,60,116);
            new Pokemon(1007, "Koraidon", new Type[]{typeList.get("Fighting"),typeList.get("Dragon")},100,135,115,85,100,135);
            new Pokemon(1008, "Miraidon", new Type[]{typeList.get("Electric"),typeList.get("Dragon")},100,85,100,135,115,135);
            new Pokemon(1009, "Walking Wake", new Type[]{typeList.get("Water"),typeList.get("Dragon")},99,83,91,125,83,109);
            new Pokemon(1010, "Iron Leaves", new Type[]{typeList.get("Grass"),typeList.get("Psychic")},90,130,88,70,108,104);
            new Pokemon(1011, "Dipplin", new Type[]{typeList.get("Grass"),typeList.get("Dragon")},80,80,110,95,80,40);
            new Pokemon(1012, "Poltchageist", new Type[]{typeList.get("Grass"),typeList.get("Ghost")},40,45,45,74,54,50);
            new Pokemon(1013, "Sinistcha", new Type[]{typeList.get("Grass"),typeList.get("Ghost")},71,60,106,121,80,70);
            new Pokemon(1014, "Okidogi", new Type[]{typeList.get("Poison"),typeList.get("Fighting")},88,128,115,58,86,80);
            new Pokemon(1015, "Munkidori", new Type[]{typeList.get("Poison"),typeList.get("Psychic")},88,75,66,130,90,106);
            new Pokemon(1016, "Fezandipiti", new Type[]{typeList.get("Poison"),typeList.get("Fairy")},88,91,82,70,125,99);
            new Pokemon(1017, "Ogerpon", new Type[]{typeList.get("Grass")},80,120,84,60,96,110);
            new Pokemon(1018, "Archaludon", new Type[]{typeList.get("Steel"),typeList.get("Dragon")},90,105,130,125,65,85);
            new Pokemon(1019, "Hydrapple", new Type[]{typeList.get("Grass"),typeList.get("Dragon")},106,80,110,120,80,44);
            new Pokemon(1020, "Gouging Fire", new Type[]{typeList.get("Fire"),typeList.get("Dragon")},105,115,121,65,93,91);
            new Pokemon(1021, "Raging Bolt", new Type[]{typeList.get("Electric"),typeList.get("Dragon")},125,73,91,137,89,75);
            new Pokemon(1022, "Iron Boulder", new Type[]{typeList.get("Rock"),typeList.get("Psychic")},90,120,80,68,108,124);
            new Pokemon(1023, "Iron Crown", new Type[]{typeList.get("Steel"),typeList.get("Psychic")},90,72,100,122,108,98);
            new Pokemon(1024, "Terapagos Normal Form", new Type[]{typeList.get("Normal")},90,65,85,65,85,60);
            new Pokemon(1025, "Pecharunt", new Type[]{typeList.get("Poison"),typeList.get("Ghost")},88,88,160,88,88,88);
        }
        private static void initViable(){
            final String[] viableMons = new String[]{
                    "Abomasnow",
                    "Alolan Ninetales",
                    "Amoonguss",
                    "Araquanid",
                    "Arcanine",
                    "Armarouge",
                    "Basculegion Female",
                    "Bloodmoon Ursaluna",
                    "Brute Bonnet",
                    "Calyrex",
                    "Chi-Yu",
                    "Chien-Pao",
                    "Clefairy",
                    "Comfey",
                    "Cresselia",
                    "Dialga",
                    "Dragapult",
                    "Dragonite",
                    "Eternatus",
                    "Farigiraf",
                    "Galarian Moltres",
                    "Garchomp",
                    "Garganacl",
                    "Gholdengo",
                    "Glimmora",
                    "Gliscor",
                    "Gouging Fire",
                    "Grimmsnarl",
                    "Groudon",
                    "Gyarados",
                    "Hatterene",
                    "Heatran",
                    "Hippowdon",
                    "Hisuian Arcanine",
                    "Hisuian Typhlosion",
                    "Ho-Oh",
                    "Houndstone",
                    "Ice Rider Calyrex",
                    "Incineroar",
                    "Indeedee Female",
                    "Iron Boulder",
                    "Iron Bundle",
                    "Iron Crown",
                    "Iron Jugulis",
                    "Kingambit",
                    "Koraidon",
                    "Kyogre",
                    "Kyurem",
                    "Black Kyurem",
                    "White Kyurem",
                    "Landorus Therian Forme",
                    "Landorus Incarnate Forme",
                    "Lugia",
                    "Lunala",
                    "Maushold",
                    "Meowscarada",
                    "Metagross",
                    "Mewtwo",
                    "Milotic",
                    "Miraidon",
                    "Murkrow",
                    "Necrozma",
                    "Dawn Wings Necrozma",
                    "Dusk Mane Necrozma",
                    "Ninetales",
                    "Ogerpon",
                    "Dialga Origin Forme",
                    "Giratina Origin Forme",
                    "Palkia Origin Forme",
                    "Palkia",
                    "Pelipper",
                    "Politoed",
                    "Porygon2",
                    "Raging Bolt",
                    "Rayquaza",
                    "Regidrago",
                    "Regieleki",
                    "Reshiram",
                    "Rillaboom",
                    "Roaring Moon",
                    "Sableye",
                    "Scizor",
                    "Shadow Rider Calyrex",
                    "Sinistcha",
                    "Sneasler",
                    "Solgaleo",
                    "Talonflame",
                    "Terapagos Normal Form",
                    "Thundurus Incarnate Forme",
                    "Ting-Lu",
                    "Torkoal",
                    "Tornadus Incarnate Forme",
                    "Tyranitar",
                    "Ursaluna",
                    "Volcanion",
                    "Walking Wake",
                    "Whimsicott",
                    "Zacian Crowned Sword",
                    "Zamazenta Crowned Shield",
                    "Zekrom"
            };

            for(String currentMon:viableMons){viableList.put(currentMon,getPokemon(currentMon));}
        }
    }

    static public class Move{
        String name;
        int baseDamage;
        String type;
        Constants.MOVE_CATS moveCategory;

        private Move(String name, int baseDamage, String type, Constants.MOVE_CATS moveCategory) {
            this.name = name;
            this.baseDamage = baseDamage;
            this.type = type;
            this.moveCategory = moveCategory;

            moveList.put(name, this);
        }

        protected static void init(){
            new Move("Absorb", 20, "Grass", Constants.MOVE_CATS.Special);
            new Move("Accelerock", 40, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Acid", 40, "Poison", Constants.MOVE_CATS.Special);
            new Move("Acid Spray", 40, "Poison", Constants.MOVE_CATS.Special);
            new Move("Acrobatics", 55, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Aerial Ace", 60, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Aeroblast", 100, "Flying", Constants.MOVE_CATS.Special);
            new Move("Air Cutter", 60, "Flying", Constants.MOVE_CATS.Special);
            new Move("Air Slash", 75, "Flying", Constants.MOVE_CATS.Special);
            new Move("Alluring Voice", 80, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Anchor Shot", 80, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Ancient Power", 60, "Rock", Constants.MOVE_CATS.Special);
            new Move("Apple Acid", 80, "Grass", Constants.MOVE_CATS.Special);
            new Move("Aqua Cutter", 70, "Water", Constants.MOVE_CATS.Physical);
            new Move("Aqua Jet", 40, "Water", Constants.MOVE_CATS.Physical);
            new Move("Aqua Step", 80, "Water", Constants.MOVE_CATS.Physical);
            new Move("Aqua Tail", 90, "Water", Constants.MOVE_CATS.Physical);
            new Move("Arm Thrust", 15, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Armor Cannon", 120, "Fire", Constants.MOVE_CATS.Special);
            new Move("Assurance", 60, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Astonish", 30, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Astral Barrage", 120, "Ghost", Constants.MOVE_CATS.Special);
            new Move("Attack Order", 90, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Aura Sphere", 80, "Fighting", Constants.MOVE_CATS.Special);
            new Move("Aura Wheel", 110, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Aurora Beam", 65, "Ice", Constants.MOVE_CATS.Special);
            new Move("Avalanche", 60, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Axe Kick", 120, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Baddy Bad", 80, "Dark", Constants.MOVE_CATS.Special);
            new Move("Barb Barrage", 60, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Barrage", 15, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Beak Blast", 100, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Behemoth Bash", 100, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Behemoth Blade", 100, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Belch", 120, "Poison", Constants.MOVE_CATS.Special);
            new Move("Bind", 15, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Bite", 60, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Bitter Blade", 90, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Bitter Malice", 75, "Ghost", Constants.MOVE_CATS.Special);
            new Move("Blast Burn", 150, "Fire", Constants.MOVE_CATS.Special);
            new Move("Blaze Kick", 85, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Blazing Torque", 80, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Bleakwind Storm", 100, "Flying", Constants.MOVE_CATS.Special);
            new Move("Blizzard", 110, "Ice", Constants.MOVE_CATS.Special);
            new Move("Blood Moon", 140, "Normal", Constants.MOVE_CATS.Special);
            new Move("Blue Flare", 130, "Fire", Constants.MOVE_CATS.Special);
            new Move("Body Press", 80, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Body Slam", 85, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Bolt Beak", 85, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Bolt Strike", 130, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Bone Club", 65, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Bone Rush", 25, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Bonemerang", 50, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Boomburst", 140, "Normal", Constants.MOVE_CATS.Special);
            new Move("Bounce", 85, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Bouncy Bubble", 60, "Water", Constants.MOVE_CATS.Special);
            new Move("Branch Poke", 40, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Brave Bird", 120, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Breaking Swipe", 60, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Brick Break", 75, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Brine", 65, "Water", Constants.MOVE_CATS.Special);
            new Move("Brutal Swing", 60, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Bubble", 40, "Water", Constants.MOVE_CATS.Special);
            new Move("Bubble Beam", 65, "Water", Constants.MOVE_CATS.Special);
            new Move("Bug Bite", 60, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Bug Buzz", 90, "Bug", Constants.MOVE_CATS.Special);
            new Move("Bulldoze", 60, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Bullet Punch", 40, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Bullet Seed", 25, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Burn Up", 130, "Fire", Constants.MOVE_CATS.Special);
            new Move("Burning Jealousy", 70, "Fire", Constants.MOVE_CATS.Special);
            new Move("Buzzy Buzz", 60, "Electric", Constants.MOVE_CATS.Special);
            new Move("Ceaseless Edge", 65, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Charge Beam", 50, "Electric", Constants.MOVE_CATS.Special);
            new Move("Chatter", 65, "Flying", Constants.MOVE_CATS.Special);
            new Move("Chilling Water", 50, "Water", Constants.MOVE_CATS.Special);
            new Move("Chip Away", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Chloroblast", 150, "Grass", Constants.MOVE_CATS.Special);
            new Move("Circle Throw", 60, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Clamp", 35, "Water", Constants.MOVE_CATS.Physical);
            new Move("Clanging Scales", 110, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Clear Smog", 50, "Poison", Constants.MOVE_CATS.Special);
            new Move("Close Combat", 120, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Collision Course", 100, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Combat Torque", 100, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Comet Punch", 18, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Confusion", 50, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Constrict", 10, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Core Enforcer", 100, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Covet", 60, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Crabhammer", 100, "Water", Constants.MOVE_CATS.Physical);
            new Move("Cross Chop", 100, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Cross Poison", 70, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Crunch", 80, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Crush Claw", 75, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Cut", 50, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Dark Pulse", 80, "Dark", Constants.MOVE_CATS.Special);
            new Move("Darkest Lariat", 85, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Dazzling Gleam", 80, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Diamond Storm", 100, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Dig", 80, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Dire Claw", 80, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Disarming Voice", 40, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Discharge", 80, "Electric", Constants.MOVE_CATS.Special);
            new Move("Dive", 80, "Water", Constants.MOVE_CATS.Physical);
            new Move("Dizzy Punch", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Doom Desire", 140, "Steel", Constants.MOVE_CATS.Special);
            new Move("Double Hit", 35, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Double Iron Bash", 60, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Double Kick", 30, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Double Shock", 120, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Double Slap", 15, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Double-Edge", 120, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Draco Meteor", 130, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Dragon Ascent", 120, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Dragon Breath", 60, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Dragon Claw", 80, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Dragon Darts", 50, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Dragon Energy", 150, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Dragon Hammer", 90, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Dragon Pulse", 85, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Dragon Rush", 100, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Dragon Tail", 60, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Drain Punch", 75, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Draining Kiss", 50, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Dream Eater", 100, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Drill Peck", 80, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Drill Run", 80, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Drum Beating", 80, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Dual Chop", 40, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Dual Wingbeat", 40, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Dynamax Cannon", 100, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Dynamic Punch", 100, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Earth Power", 90, "Ground", Constants.MOVE_CATS.Special);
            new Move("Earthquake", 100, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Echoed Voice", 40, "Normal", Constants.MOVE_CATS.Special);
            new Move("Eerie Spell", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Egg Bomb", 100, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Electro Drift", 100, "Electric", Constants.MOVE_CATS.Special);
            new Move("Electro Shot", 130, "Electric", Constants.MOVE_CATS.Special);
            new Move("Electroweb", 55, "Electric", Constants.MOVE_CATS.Special);
            new Move("Ember", 40, "Fire", Constants.MOVE_CATS.Special);
            new Move("Energy Ball", 90, "Grass", Constants.MOVE_CATS.Special);
            new Move("Eruption", 150, "Fire", Constants.MOVE_CATS.Special);
            new Move("Esper Wing", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Eternabeam", 160, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Expanding Force", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Explosion", 250, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Extrasensory", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Extreme Speed", 80, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Facade", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Fairy Wind", 40, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Fake Out", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("False Surrender", 80, "Dark", Constants.MOVE_CATS.Physical);
            new Move("False Swipe", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Feint", 30, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Feint Attack", 60, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Fell Stinger", 50, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Fickle Beam", 80, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Fiery Dance", 80, "Fire", Constants.MOVE_CATS.Special);
            new Move("Fiery Wrath", 90, "Dark", Constants.MOVE_CATS.Special);
            new Move("Fire Blast", 110, "Fire", Constants.MOVE_CATS.Special);
            new Move("Fire Fang", 65, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Fire Lash", 80, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Fire Pledge", 80, "Fire", Constants.MOVE_CATS.Special);
            new Move("Fire Punch", 75, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Fire Spin", 35, "Fire", Constants.MOVE_CATS.Special);
            new Move("First Impression", 90, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Fishious Rend", 85, "Water", Constants.MOVE_CATS.Physical);
            new Move("Flame Burst", 70, "Fire", Constants.MOVE_CATS.Special);
            new Move("Flame Charge", 50, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Flame Wheel", 60, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Flamethrower", 90, "Fire", Constants.MOVE_CATS.Special);
            new Move("Flare Blitz", 120, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Flash Cannon", 80, "Steel", Constants.MOVE_CATS.Special);
            new Move("Fleur Cannon", 130, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Flip Turn", 60, "Water", Constants.MOVE_CATS.Physical);
            new Move("Floaty Fall", 90, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Flower Trick", 70, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Fly", 90, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Flying Press", 100, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Focus Blast", 120, "Fighting", Constants.MOVE_CATS.Special);
            new Move("Focus Punch", 150, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Force Palm", 60, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Foul Play", 95, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Freeze Shock", 140, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Freeze-Dry", 70, "Ice", Constants.MOVE_CATS.Special);
            new Move("Freezing Glare", 90, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Freezy Frost", 100, "Ice", Constants.MOVE_CATS.Special);
            new Move("Frenzy Plant", 150, "Grass", Constants.MOVE_CATS.Special);
            new Move("Frost Breath", 60, "Ice", Constants.MOVE_CATS.Special);
            new Move("Fury Attack", 15, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Fury Cutter", 40, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Fury Swipes", 18, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Fusion Bolt", 100, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Fusion Flare", 100, "Fire", Constants.MOVE_CATS.Special);
            new Move("Future Sight", 120, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Gear Grind", 50, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Giga Drain", 75, "Grass", Constants.MOVE_CATS.Special);
            new Move("Giga Impact", 150, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Gigaton Hammer", 160, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Glacial Lance", 120, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Glaciate", 65, "Ice", Constants.MOVE_CATS.Special);
            new Move("Glaive Rush", 120, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Glitzy Glow", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Grass Pledge", 80, "Grass", Constants.MOVE_CATS.Special);
            new Move("Grassy Glide", 55, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Grav Apple", 80, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Gunk Shot", 120, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Gust", 40, "Flying", Constants.MOVE_CATS.Special);
            new Move("Hammer Arm", 100, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Head Charge", 120, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Head Smash", 150, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Headbutt", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Headlong Rush", 120, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Heart Stamp", 60, "Psychic", Constants.MOVE_CATS.Physical);
            new Move("Heat Wave", 95, "Fire", Constants.MOVE_CATS.Special);
            new Move("Hex", 65, "Ghost", Constants.MOVE_CATS.Special);
            new Move("Hidden Power", 60, "Normal", Constants.MOVE_CATS.Special);
            new Move("High Horsepower", 95, "Ground", Constants.MOVE_CATS.Physical);
            new Move("High Jump Kick", 130, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Hold Back", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Horn Attack", 65, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Horn Leech", 75, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Hurricane", 110, "Flying", Constants.MOVE_CATS.Special);
            new Move("Hydro Cannon", 150, "Water", Constants.MOVE_CATS.Special);
            new Move("Hydro Pump", 110, "Water", Constants.MOVE_CATS.Special);
            new Move("Hydro Steam", 80, "Water", Constants.MOVE_CATS.Special);
            new Move("Hyper Beam", 150, "Normal", Constants.MOVE_CATS.Special);
            new Move("Hyper Drill", 100, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Hyper Fang", 80, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Hyper Voice", 90, "Normal", Constants.MOVE_CATS.Special);
            new Move("Hyperspace Fury", 100, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Hyperspace Hole", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Ice Ball", 30, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Ice Beam", 90, "Ice", Constants.MOVE_CATS.Special);
            new Move("Ice Burn", 140, "Ice", Constants.MOVE_CATS.Special);
            new Move("Ice Fang", 65, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Ice Hammer", 100, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Ice Punch", 75, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Ice Shard", 40, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Ice Spinner", 80, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Icicle Crash", 85, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Icicle Spear", 25, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Icy Wind", 55, "Ice", Constants.MOVE_CATS.Special);
            new Move("Incinerate", 60, "Fire", Constants.MOVE_CATS.Special);
            new Move("Infernal Parade", 60, "Ghost", Constants.MOVE_CATS.Special);
            new Move("Inferno", 100, "Fire", Constants.MOVE_CATS.Special);
            new Move("Infestation", 20, "Bug", Constants.MOVE_CATS.Special);
            new Move("Iron Head", 80, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Iron Tail", 100, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Ivy Cudgel", 100, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Jaw Lock", 80, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Jet Punch", 60, "Water", Constants.MOVE_CATS.Physical);
            new Move("Judgment", 100, "Normal", Constants.MOVE_CATS.Special);
            new Move("Jump Kick", 100, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Karate Chop", 50, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Knock Off", 65, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Kowtow Cleave", 85, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Land's Wrath", 90, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Lash Out", 75, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Last Resort", 140, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Last Respects", 50, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Lava Plume", 80, "Fire", Constants.MOVE_CATS.Special);
            new Move("Leaf Blade", 90, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Leaf Storm", 130, "Grass", Constants.MOVE_CATS.Special);
            new Move("Leaf Tornado", 65, "Grass", Constants.MOVE_CATS.Special);
            new Move("Leafage", 40, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Leech Life", 80, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Lick", 30, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Light of Ruin", 140, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Liquidation", 85, "Water", Constants.MOVE_CATS.Physical);
            new Move("Low Sweep", 65, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Lumina Crash", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Lunge", 80, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Luster Purge", 95, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Mach Punch", 40, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Magical Leaf", 60, "Grass", Constants.MOVE_CATS.Special);
            new Move("Magical Torque", 100, "Fairy", Constants.MOVE_CATS.Physical);
            new Move("Magma Storm", 100, "Fire", Constants.MOVE_CATS.Special);
            new Move("Magnet Bomb", 60, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Make It Rain", 120, "Steel", Constants.MOVE_CATS.Special);
            new Move("Malignant Chain", 100, "Poison", Constants.MOVE_CATS.Special);
            new Move("Matcha Gotcha", 80, "Grass", Constants.MOVE_CATS.Special);
            new Move("Mega Drain", 40, "Grass", Constants.MOVE_CATS.Special);
            new Move("Mega Kick", 120, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Mega Punch", 80, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Megahorn", 120, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Metal Claw", 50, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Meteor Assault", 150, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Meteor Beam", 120, "Rock", Constants.MOVE_CATS.Special);
            new Move("Meteor Mash", 90, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Mighty Cleave", 95, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Mind Blown", 150, "Fire", Constants.MOVE_CATS.Special);
            new Move("Mirror Shot", 65, "Steel", Constants.MOVE_CATS.Special);
            new Move("Mist Ball", 95, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Misty Explosion", 100, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Moonblast", 95, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Moongeist Beam", 100, "Ghost", Constants.MOVE_CATS.Special);
            new Move("Mortal Spin", 30, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Mountain Gale", 100, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Mud Bomb", 65, "Ground", Constants.MOVE_CATS.Special);
            new Move("Mud Shot", 55, "Ground", Constants.MOVE_CATS.Special);
            new Move("Mud-Slap", 20, "Ground", Constants.MOVE_CATS.Special);
            new Move("Muddy Water", 90, "Water", Constants.MOVE_CATS.Special);
            new Move("Multi-Attack", 120, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Mystical Fire", 75, "Fire", Constants.MOVE_CATS.Special);
            new Move("Mystical Power", 70, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Needle Arm", 60, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Night Daze", 85, "Dark", Constants.MOVE_CATS.Special);
            new Move("Night Slash", 70, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Noxious Torque", 100, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Nuzzle", 20, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Oblivion Wing", 80, "Flying", Constants.MOVE_CATS.Special);
            new Move("Octazooka", 65, "Water", Constants.MOVE_CATS.Special);
            new Move("Ominous Wind", 60, "Ghost", Constants.MOVE_CATS.Special);
            new Move("Order Up", 80, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Origin Pulse", 110, "Water", Constants.MOVE_CATS.Special);
            new Move("Outrage", 120, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Overdrive", 80, "Electric", Constants.MOVE_CATS.Special);
            new Move("Overheat", 130, "Fire", Constants.MOVE_CATS.Special);
            new Move("Parabolic Charge", 65, "Electric", Constants.MOVE_CATS.Special);
            new Move("Pay Day", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Payback", 50, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Peck", 35, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Petal Blizzard", 90, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Petal Dance", 120, "Grass", Constants.MOVE_CATS.Special);
            new Move("Phantom Force", 90, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Photon Geyser", 100, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Pin Missile", 25, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Plasma Fists", 100, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Play Rough", 90, "Fairy", Constants.MOVE_CATS.Physical);
            new Move("Pluck", 60, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Poison Fang", 50, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Poison Jab", 80, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Poison Sting", 15, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Poison Tail", 50, "Poison", Constants.MOVE_CATS.Physical);
            new Move("Pollen Puff", 90, "Bug", Constants.MOVE_CATS.Special);
            new Move("Poltergeist", 110, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Population Bomb", 20, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Pounce", 50, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Pound", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Powder Snow", 40, "Ice", Constants.MOVE_CATS.Special);
            new Move("Power Gem", 80, "Rock", Constants.MOVE_CATS.Special);
            new Move("Power Trip", 20, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Power Whip", 120, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Power-Up Punch", 40, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Precipice Blades", 120, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Prismatic Laser", 160, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Psybeam", 65, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Psyblade", 80, "Psychic", Constants.MOVE_CATS.Physical);
            new Move("Psychic", 90, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Psychic Fangs", 85, "Psychic", Constants.MOVE_CATS.Physical);
            new Move("Psychic Noise", 75, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Psycho Boost", 140, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Psycho Cut", 70, "Psychic", Constants.MOVE_CATS.Physical);
            new Move("Psyshield Bash", 70, "Psychic", Constants.MOVE_CATS.Physical);
            new Move("Psyshock", 80, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Psystrike", 100, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Pursuit", 40, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Pyro Ball", 120, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Quick Attack", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Rage", 20, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Rage Fist", 50, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Raging Bull", 90, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Raging Fury", 120, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Rapid Spin", 50, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Razor Leaf", 55, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Razor Shell", 75, "Water", Constants.MOVE_CATS.Physical);
            new Move("Razor Wind", 80, "Normal", Constants.MOVE_CATS.Special);
            new Move("Relic Song", 75, "Normal", Constants.MOVE_CATS.Special);
            new Move("Retaliate", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Revelation Dance", 90, "Normal", Constants.MOVE_CATS.Special);
            new Move("Revenge", 60, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Rising Voltage", 70, "Electric", Constants.MOVE_CATS.Special);
            new Move("Roar of Time", 150, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Rock Blast", 25, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Rock Climb", 90, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Rock Slide", 75, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Rock Smash", 40, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Rock Throw", 50, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Rock Tomb", 60, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Rock Wrecker", 150, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Rolling Kick", 60, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Rollout", 30, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Round", 60, "Normal", Constants.MOVE_CATS.Special);
            new Move("Sacred Fire", 100, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Sacred Sword", 90, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Salt Cure", 40, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Sand Tomb", 35, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Sandsear Storm", 100, "Ground", Constants.MOVE_CATS.Special);
            new Move("Sappy Seed", 100, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Scald", 80, "Water", Constants.MOVE_CATS.Special);
            new Move("Scale Shot", 25, "Dragon", Constants.MOVE_CATS.Physical);
            new Move("Scorching Sands", 70, "Ground", Constants.MOVE_CATS.Special);
            new Move("Scratch", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Searing Shot", 100, "Fire", Constants.MOVE_CATS.Special);
            new Move("Secret Power", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Secret Sword", 85, "Fighting", Constants.MOVE_CATS.Special);
            new Move("Seed Bomb", 80, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Seed Flare", 120, "Grass", Constants.MOVE_CATS.Special);
            new Move("Self-Destruct", 200, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Shadow Ball", 80, "Ghost", Constants.MOVE_CATS.Special);
            new Move("Shadow Bone", 85, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Shadow Claw", 70, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Shadow Force", 120, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Shadow Punch", 60, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Shadow Sneak", 40, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Shell Side Arm", 90, "Poison", Constants.MOVE_CATS.Special);
            new Move("Shell Trap", 150, "Fire", Constants.MOVE_CATS.Special);
            new Move("Shock Wave", 60, "Electric", Constants.MOVE_CATS.Special);
            new Move("Signal Beam", 75, "Bug", Constants.MOVE_CATS.Special);
            new Move("Silver Wind", 60, "Bug", Constants.MOVE_CATS.Special);
            new Move("Sizzly Slide", 60, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Skitter Smack", 70, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Skull Bash", 130, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Sky Attack", 140, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Sky Drop", 60, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Sky Uppercut", 85, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Slam", 80, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Slash", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Sludge", 65, "Poison", Constants.MOVE_CATS.Special);
            new Move("Sludge Bomb", 90, "Poison", Constants.MOVE_CATS.Special);
            new Move("Sludge Wave", 95, "Poison", Constants.MOVE_CATS.Special);
            new Move("Smack Down", 50, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Smart Strike", 70, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Smelling Salts", 70, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Smog", 30, "Poison", Constants.MOVE_CATS.Special);
            new Move("Snap Trap", 35, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Snarl", 55, "Dark", Constants.MOVE_CATS.Special);
            new Move("Snipe Shot", 80, "Water", Constants.MOVE_CATS.Special);
            new Move("Snore", 50, "Normal", Constants.MOVE_CATS.Special);
            new Move("Solar Beam", 120, "Grass", Constants.MOVE_CATS.Special);
            new Move("Solar Blade", 125, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Spacial Rend", 100, "Dragon", Constants.MOVE_CATS.Special);
            new Move("Spark", 65, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Sparkling Aria", 90, "Water", Constants.MOVE_CATS.Special);
            new Move("Sparkly Swirl", 120, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Spectral Thief", 90, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Spike Cannon", 20, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Spin Out", 100, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Spirit Break", 75, "Fairy", Constants.MOVE_CATS.Physical);
            new Move("Spirit Shackle", 80, "Ghost", Constants.MOVE_CATS.Physical);
            new Move("Splishy Splash", 90, "Water", Constants.MOVE_CATS.Special);
            new Move("Springtide Storm", 100, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Steam Eruption", 110, "Water", Constants.MOVE_CATS.Special);
            new Move("Steamroller", 65, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Steel Beam", 140, "Steel", Constants.MOVE_CATS.Special);
            new Move("Steel Roller", 130, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Steel Wing", 70, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Stomp", 65, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Stomping Tantrum", 75, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Stone Axe", 65, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Stone Edge", 100, "Rock", Constants.MOVE_CATS.Physical);
            new Move("Stored Power", 20, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Storm Throw", 60, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Strange Steam", 90, "Fairy", Constants.MOVE_CATS.Special);
            new Move("Strength", 80, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Struggle", 50, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Struggle Bug", 50, "Bug", Constants.MOVE_CATS.Special);
            new Move("Submission", 80, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Sucker Punch", 70, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Sunsteel Strike", 100, "Steel", Constants.MOVE_CATS.Physical);
            new Move("Supercell Slam", 100, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Superpower", 120, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Surf", 90, "Water", Constants.MOVE_CATS.Special);
            new Move("Surging Strikes", 25, "Water", Constants.MOVE_CATS.Physical);
            new Move("Swift", 60, "Normal", Constants.MOVE_CATS.Special);
            new Move("Synchronoise", 120, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Syrup Bomb", 60, "Grass", Constants.MOVE_CATS.Special);
            new Move("Tachyon Cutter", 50, "Steel", Constants.MOVE_CATS.Special);
            new Move("Tackle", 40, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Tail Slap", 25, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Take Down", 90, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Techno Blast", 120, "Normal", Constants.MOVE_CATS.Special);
            new Move("Temper Flare", 75, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Tera Blast", 80, "Normal", Constants.MOVE_CATS.Special);
            new Move("Tera Starstorm", 120, "Normal", Constants.MOVE_CATS.Special);
            new Move("Terrain Pulse", 50, "Normal", Constants.MOVE_CATS.Special);
            new Move("Thief", 60, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Thousand Arrows", 90, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Thousand Waves", 90, "Ground", Constants.MOVE_CATS.Physical);
            new Move("Thrash", 120, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Throat Chop", 80, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Thunder", 110, "Electric", Constants.MOVE_CATS.Special);
            new Move("Thunder Cage", 80, "Electric", Constants.MOVE_CATS.Special);
            new Move("Thunder Fang", 65, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Thunder Punch", 75, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Thunder Shock", 40, "Electric", Constants.MOVE_CATS.Special);
            new Move("Thunderbolt", 90, "Electric", Constants.MOVE_CATS.Special);
            new Move("Thunderclap", 70, "Electric", Constants.MOVE_CATS.Special);
            new Move("Thunderous Kick", 90, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Torch Song", 80, "Fire", Constants.MOVE_CATS.Special);
            new Move("Trailblaze", 50, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Tri Attack", 80, "Normal", Constants.MOVE_CATS.Special);
            new Move("Triple Arrows", 90, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Triple Axel", 20, "Ice", Constants.MOVE_CATS.Physical);
            new Move("Triple Dive", 30, "Water", Constants.MOVE_CATS.Physical);
            new Move("Triple Kick", 10, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Trop Kick", 70, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Twin Beam", 40, "Psychic", Constants.MOVE_CATS.Special);
            new Move("Twineedle", 25, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Twister", 40, "Dragon", Constants.MOVE_CATS.Special);
            new Move("U-turn", 70, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Upper Hand", 65, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Uproar", 90, "Normal", Constants.MOVE_CATS.Special);
            new Move("V-create", 180, "Fire", Constants.MOVE_CATS.Physical);
            new Move("Vacuum Wave", 40, "Fighting", Constants.MOVE_CATS.Special);
            new Move("Venoshock", 65, "Poison", Constants.MOVE_CATS.Special);
            new Move("Vine Whip", 45, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Vise Grip", 55, "Normal", Constants.MOVE_CATS.Physical);
            new Move("Vital Throw", 70, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Volt Switch", 70, "Electric", Constants.MOVE_CATS.Special);
            new Move("Volt Tackle", 120, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Wake-Up Slap", 70, "Fighting", Constants.MOVE_CATS.Physical);
            new Move("Water Gun", 40, "Water", Constants.MOVE_CATS.Special);
            new Move("Water Pledge", 80, "Water", Constants.MOVE_CATS.Special);
            new Move("Water Pulse", 60, "Water", Constants.MOVE_CATS.Special);
            new Move("Water Shuriken", 15, "Water", Constants.MOVE_CATS.Special);
            new Move("Water Spout", 150, "Water", Constants.MOVE_CATS.Special);
            new Move("Waterfall", 80, "Water", Constants.MOVE_CATS.Physical);
            new Move("Wave Crash", 120, "Water", Constants.MOVE_CATS.Physical);
            new Move("Weather Ball", 50, "Normal", Constants.MOVE_CATS.Special);
            new Move("Whirlpool", 35, "Water", Constants.MOVE_CATS.Special);
            new Move("Wicked Blow", 75, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Wicked Torque", 80, "Dark", Constants.MOVE_CATS.Physical);
            new Move("Wild Charge", 90, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Wildbolt Storm", 100, "Electric", Constants.MOVE_CATS.Special);
            new Move("Wing Attack", 60, "Flying", Constants.MOVE_CATS.Physical);
            new Move("Wood Hammer", 120, "Grass", Constants.MOVE_CATS.Physical);
            new Move("Wrap", 15, "Normal", Constants.MOVE_CATS.Physical);
            new Move("X-Scissor", 80, "Bug", Constants.MOVE_CATS.Physical);
            new Move("Zap Cannon", 120, "Electric", Constants.MOVE_CATS.Special);
            new Move("Zen Headbutt", 80, "Psychic", Constants.MOVE_CATS.Physical);
            new Move("Zing Zap", 80, "Electric", Constants.MOVE_CATS.Physical);
            new Move("Zippy Zap", 80, "Electric", Constants.MOVE_CATS.Physical);
        }
    }

    public static class Type {
        String name;
        String[] resistances;
        String[] weaknesses;
        String[] immunities;
        private static final String[] typeNames = new String[]{"Normal", "Fighting", "Flying", "Fire", "Grass", "Water", "Electric", "Ground", "Rock", "Dragon", "Poison", "Bug", "Ghost", "Psychic", "Ice", "Steel", "Dark", "Fairy"};
        //also maybe a section about fringe cases such as typeList.get("Grass") types being immune to spore and typeList.get("Ghost") types unable to be trapped

        public Type(String name) {
            this.name = name;
            typeList.put(name, this);
        }


        public static ArrayList<String>[] returnAllMatchups(Type[] defender){
            ArrayList<String> neutral = new ArrayList<>();
            ArrayList<String> superEffective = new ArrayList<>();
            ArrayList<String> notVeryEffective = new ArrayList<>();
            ArrayList<String> immune = new ArrayList<>();

            for(String currentType:typeNames){
                double effectiveness = 1;
            
                for(Type i:defender){effectiveness*=returnOneMatchup(i, currentType);}

                if(effectiveness>1){superEffective.add(currentType);
                }else if(effectiveness<1&&effectiveness!=0){notVeryEffective.add(currentType);
                }else if(effectiveness==0){immune.add(currentType);
                }else{neutral.add(currentType);}
            }

            return new ArrayList[]{neutral,superEffective,notVeryEffective,immune};
        }

        private static double returnOneMatchup(Type defendingType, String attackingType){
            if(defendingType==null){return 1;}

            if(Arrays.asList(defendingType.weaknesses).contains(attackingType)) {return 2;
            }else if(Arrays.asList(defendingType.resistances).contains(attackingType)){return 0.5;
            }try{if(Arrays.asList(defendingType.immunities).contains(attackingType)) {return 0;}}catch(Exception _){}

            return 1;
        }

        public static void init(){
            //type database (inits all types into the array
            for(int i=0; i <=typeNames.length-1; i++){new Type(typeNames[i]);}

            //weaknesses
            typeList.get("Normal").weaknesses = new String[]{"Fighting"};
            typeList.get("Fighting").weaknesses = new String[]{"Flying", "Psychic", "Fairy"};
            typeList.get("Flying").weaknesses = new String[]{"Rock", "Electric", "Ice"};
            typeList.get("Fire").weaknesses = new String[]{"Water", "Ground", "Rock"};
            typeList.get("Grass").weaknesses = new String[]{"Poison", "Flying", "Ice", "Fire", "Bug"};
            typeList.get("Water").weaknesses = new String[]{"Electric", "Grass"};
            typeList.get("Electric").weaknesses = new String[]{"Ground"};
            typeList.get("Ground").weaknesses = new String[]{"Water", "Ice", "Grass"};
            typeList.get("Rock").weaknesses = new String[]{"Fighting", "Water", "Grass", "Ground", "Steel"};
            typeList.get("Dragon").weaknesses = new String[]{"Ice", "Fairy"};
            typeList.get("Poison").weaknesses = new String[]{"Ground", "Psychic"};
            typeList.get("Bug").weaknesses = new String[]{"Flying", "Rock", "Fire"};
            typeList.get("Dark").weaknesses = new String[]{"Fighting", "Fairy", "Bug"};
            typeList.get("Steel").weaknesses = new String[]{"Fighting", "Fire", "Ground"};
            typeList.get("Ghost").weaknesses = new String[]{"Ghost", "Dark"};
            typeList.get("Fairy").weaknesses = new String[]{"Steel", "Poison"};
            typeList.get("Psychic").weaknesses = new String[]{"Dark", "Ghost", "Bug"};
            typeList.get("Ice").weaknesses = new String[]{"Fighting", "Rock", "Fire", "Steel"};

            //resists
            typeList.get("Normal").resistances = new String[]{};
            typeList.get("Fighting").resistances = new String[]{"Rock", "Bug", "Dark"};
            typeList.get("Flying").resistances = new String[]{"Fighting", "Bug", "Grass"};
            typeList.get("Fire").resistances = new String[]{"Bug","Fire","Grass","Steel","Steel","Ice","Fairy"};
            typeList.get("Grass").resistances = new String[]{"Water","Electric","Grass","Ground"};
            typeList.get("Water").resistances = new String[]{"Fire","Water","Ice","Steel"};
            typeList.get("Electric").resistances = new String[]{"Flying","Steel","Electric"};
            typeList.get("Ground").resistances = new String[]{"Poison","Rock"};
            typeList.get("Rock").resistances = new String[]{"Normal","Fire","Poison","Flying"};
            typeList.get("Dragon").resistances = new String[]{"Fire","Water","Grass","Electric"};
            typeList.get("Poison").resistances = new String[]{"Fighting","Poison","Grass","Bug","Fairy"};
            typeList.get("Bug").resistances = new String[]{"Fighting","Ground","Grass"};
            typeList.get("Dark").resistances = new String[]{"Ghost", "Dark"};
            typeList.get("Steel").resistances = new String[]{"Normal","Flying","Rock","Bug","Steel","Grass","Psychic","Ice","Dragon","Fairy"};
            typeList.get("Ghost").resistances = new String[]{"Poison","Bug"};
            typeList.get("Fairy").resistances = new String[]{"Fighting","Bug","Dark"};
            typeList.get("Psychic").resistances = new String[]{"Fighting","Psychic"};
            typeList.get("Ice").resistances = new String[]{"Ice"};

            //immunities
            typeList.get("Normal").immunities = new String[]{"Ghost"};
            typeList.get("Flying").immunities = new String[]{"Ground"};
            typeList.get("Steel").immunities = new String[]{"Poison"};
            typeList.get("Ghost").immunities = new String[]{"Normal", "Fighting"};
            typeList.get("Fairy").immunities = new String[]{"Dragon"};
            }
        }

    public static class Items{
        public static double getItemEffect(String input, Constants.MOVE_CATS category){
            if(input.equals("Choice Band")&&category== Constants.MOVE_CATS.Physical){return 1.5;
            }else if(input.equals("Choice Specs")&&category== Constants.MOVE_CATS.Special){return 1.5;
            }else if(input.equals("Life Orb")){return 1.3;}
            return 1;
        }

        private static void init(){
            itemList.add("No Item/Other");
            itemList.add("Choice Band");
            itemList.add("Choice Specs");
            itemList.add("Choice Scarf"); //use when u make outspeed calc
            itemList.add("Life Orb");
            //itemList.add("Booster Energy [doesn't work rn]");
        }
    }

    public static class Nature{
        public String name;
        public double attack;
        public double defense;
        public double spatk;
        public double spdef;
        public double speed;

        private Nature(String name, double attack, double defense, double spatk, double spdef, double speed){
            this.name = name;
            this.attack = attack;
            this.defense = defense;
            this.spatk = spatk;
            this.spdef = spdef;
            this.speed = speed;

            natureList.put(name, this);
        }

        private static void init(){
            new Nature("+Attack",1.1,1,1,1,1);
            new Nature("-Attack",0.9,1,1,1,1);

            new Nature("+Defense",1,1.1,1,1,1);
            new Nature("-Defense",1,0.9,1,1,1);

            new Nature("+SpAtk",1,1,1.1,1,1);
            new Nature("-SpAtk",1,1,0.9,1,1);

            new Nature("+SpDef",1,1,1,1.1,1);
            new Nature("-SpDef",1,1,1,0.9,1);

            new Nature("+Speed",1,1,1,1,1.1);
            new Nature("-Speed",1,1,1,1,0.9);

            new Nature("Neutral",1,1,1,1,1);

        }
    }

    //ughhh do this shit later
    public static class Ability{
        private static void init(){
            abilityList.add("Other Ability");
            abilityList.add("Swift Swim");
            abilityList.add("Chlorophyll");
            abilityList.add("Huge Power");
            abilityList.add("Thick Fat");
            abilityList.add("Hustle");
            abilityList.add("Guts");
            abilityList.add("Marvel Scale");
            abilityList.add("Pure Power");
            abilityList.add("Heatproof");
            abilityList.add("Dry Skin");
            abilityList.add("Iron Fist");
            abilityList.add("Poison Heal");
            abilityList.add("Adaptability");
            abilityList.add("Solar Power");
            abilityList.add("Quick Feet");
            abilityList.add("Technician");
            abilityList.add("Unaware");
            abilityList.add("Tinted Lens");
            abilityList.add("Filter");
            abilityList.add("Scrappy");
            abilityList.add("Solid Rock");
            abilityList.add("Reckless");
            abilityList.add("Flower Gift");
            abilityList.add("Sheer Force");
            abilityList.add("Sand Rush");
            abilityList.add("Analytic");
            abilityList.add("Sap Sipper");
            abilityList.add("Sand Force");
            abilityList.add("Fur Coat");
            abilityList.add("Strong Jaw");
            abilityList.add("Refrigerate");
            abilityList.add("Mega Launcher");
            abilityList.add("Grass Pelt");
            abilityList.add("Tough Claws");
            abilityList.add("Pixilate");
            abilityList.add("Aerilate");
            abilityList.add("Parental Bond");
            abilityList.add("Dark Aura");
            abilityList.add("Fairy Aura");
            abilityList.add("Delta Stream");
            abilityList.add("Steelworker");
            abilityList.add("Slush Rush");
            abilityList.add("Galvanize");
            abilityList.add("Surge Surfer");
            abilityList.add("Fluffy");
            abilityList.add("Shadow Shield");
            abilityList.add("Prism Armor");
            abilityList.add("Neuroforce");
            abilityList.add("Ice Scales");
            abilityList.add("Steely Spirit");
            abilityList.add("Gorilla Tactics");
            abilityList.add("Transistor");
            abilityList.add("Dragon's Maw");
            abilityList.add("Purifying Salt");
            abilityList.add("Well-Baked Body");
            abilityList.add("Wind Rider");
            abilityList.add("Rocky Payload");
            abilityList.add("Protosynthesis");
            abilityList.add("Quark Drive");
            abilityList.add("Vessel of Ruin");
            abilityList.add("Sword of Ruin");
            abilityList.add("Tablets of Ruin");
            abilityList.add("Beads of Ruin");
            abilityList.add("Orichalcum Pulse");
            abilityList.add("Hadron Engine");
            abilityList.add("Sharpness");
            abilityList.add("Mind's Eye");
        }
    }
}