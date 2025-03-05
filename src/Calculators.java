public class Calculators extends Database {
    //calculation for all stats (excluding HP)
    //more info: https://bulbapedia.bulbagarden.net/wiki/Stat#Generation_III_onward
    //must be cast to double to prevent floating point error but has to be cast back to int because all pokemon calculations *always* round down
    public static int statCalculation(int baseStat, int IV, int EV, double nature, int level, int boostCount){
        EV/=4; //divides ev by 4 because ev is divided by 4 in stat calcs
        int stat = -1;
        try {stat=(int)((int)((((double)((2*baseStat+IV+EV)*level)/100)+5)*nature)*getBoostModifier(boostCount));
        }catch (Exception e){ErrorPrinter.handler(ErrorPrinter.ERROR_CODE.ERR_CC_STAT_CALCULATION_ERROR, e);}
        return stat;
    }

    //finds what stat boost you need to be at to be faster than a given stat
    public static int findLeastSpeedEVs(EVCalculatorUI.CurrentPokemon you, int targetStat, int boostCount){

        for(int EV = 0; EV<=252; EV++){
            final int stat = statCalculation(you.base.baseSpeed,31,EV,you.nature.speed,you.level,boostCount);
            if(Constants.DEBUG_DAMAGE_MODE){System.out.println("\nOPP SPEED STAT: "+targetStat+"\nYOUR SPEED STAT: "+stat+"\nYOUR SPEED EV: "+EV+"\nOPP SPPEED BOOST: "+HelperMethods.getComponentValue("Right-Side Speed Boost", true)+"\nOPP SPPED BOOST MODIFIER: "+getBoostModifier(Double.parseDouble(HelperMethods.getComponentValue("Right-Side Speed Boost", true))));}
            if(stat>targetStat){return EV;}
        }
        return -1;
    }

    //finds what stat boost you need to ohko
    public static int[] findLeastHPEVs(EVCalculatorUI.CurrentPokemon opponentMon, EVCalculatorUI.CurrentPokemon targetMon, Move move, String weather, boolean spread){
        int oppAttackStat = opponentMon.attackStat;
        int targetBaseHP = targetMon.base.baseHP;
        int targetDefStat = targetMon.defStat;

        if(move.moveCategory== Constants.MOVE_CATS.Special){
            targetDefStat = targetMon.spDefStat;
            oppAttackStat = opponentMon.spAttackStat;
        }
        final int[] EVRolls = {-1,-1,-1};

        int index = 0;
        final int damage = damageCalc(targetMon,opponentMon,move,spread,weather);

        for(double currentRoll:Constants.ROLLS){
            if(Constants.DEBUG_DAMAGE_MODE){System.out.println();}
            for (int EV = 0; EV <= 252; EV += 4){//ev goes up by 4 bc the stat only goes up every 4 evs
                final int targetCalcedHPStat = calcHP(EV, targetMon.level, targetBaseHP);

                if((damage*currentRoll)<targetCalcedHPStat){
                    if(Constants.DEBUG_DAMAGE_MODE){System.out.printf("\n[RESULT FOUND!]\nyour baseHP stat: %d\nyour defense stat: %d\nEV: %d\nyour calculated HP stat: %d\nopp attack stat %d\nopp attack ev: %d\ndamage: %d\nroll:%f\n--------------------------------------------\n[END].\n", targetBaseHP, targetDefStat,EV, targetCalcedHPStat, oppAttackStat, opponentMon.attackEV,damage,currentRoll);}
                    EVRolls[index] = EV;
                    break;
                }
            }
            index++;
        }
        return EVRolls;
    }

    //"why do you cast to int so much?" everything rounds down. all calculations *always* round down.
    private static int damageCalc(EVCalculatorUI.CurrentPokemon attacker, EVCalculatorUI.CurrentPokemon defender,Move move, boolean spread, String weather){
        try {
            double rawDamage = getRawDamage(attacker, defender, move); //damage before other factors such as stab, weather, abilities, etc

            final double finalDamage = other(attacker, defender, rawDamage, move, spread, weather); //other factors such as stab/weather

            if(Constants.DEBUG_DAMAGE_MODE){System.out.println("\nmove bp: "+move.baseDamage+"\nmove category: "+move.moveCategory+"\nraw damage: "+rawDamage+"\nfinal damage: " + finalDamage+"\n------[END DAMAGE CALC]------\n");}

            return (int)finalDamage;
        }catch(Exception e){
            ErrorPrinter.handler(ErrorPrinter.ERROR_CODE.ERR_CC_DAMAGE_CALCULATION_ERROR, e);
            return -1;
        }
    }

    //rawDamage is the damage calc before any situational modifiers. more info here: https://bulbapedia.bulbagarden.net/wiki/Damage#Generation_V_onward
    private static double getRawDamage(EVCalculatorUI.CurrentPokemon attacker, EVCalculatorUI.CurrentPokemon defender, Move move) {
        double attackerLevel = (((double)(2* attacker.level)/5)+2); //this is done here to decrease verbosity of the rawDamage calc and make it more readable and testable
        final double AD = (double) attacker.attackStat/ defender.defStat; //attack divided by defense. this is done in a seperate variable to decrease verbosity.
        return (int)(((attackerLevel* move.baseDamage*AD)/50)+2);
    }

    //get stat boost modifier
    private static double getBoostModifier(double boostCount){
        if(boostCount<=-1){return 2/(2+Math.abs(boostCount));
        }else{return (boostCount+2)/2;}
    }

    //finds what stat boost you need to ohko
    public static int[] findLeastAttackEVs(EVCalculatorUI.CurrentPokemon you, EVCalculatorUI.CurrentPokemon opp, Move move, String weather, boolean spread){
        int baseStat = you.base.baseAttack;
        double nature = you.nature.attack;
        int boostCount = you.attackBoost;
        int defenderStat = opp.defStat;

        if(move.moveCategory== Constants.MOVE_CATS.Special){
            baseStat = you.base.baseSpatk;
            nature = you.nature.spatk;
            boostCount = you.spAttackBoost;
            defenderStat = opp.spDefStat;
        }

        int index = 0;
        final int[] EVrolls = {-1,-1,-1};

        for(double currentRoll:Constants.ROLLS) {
            for (int EV = 0; EV <= 252; EV += 4) {//ev goes up by 4 bc the stat only goes up every 4 evs
                final int stat = statCalculation(baseStat, 31, EV, nature, you.level, boostCount);
                final int damage = (int)(damageCalc(you,opp,move,spread,weather) * currentRoll);

                if(Constants.DEBUG_DAMAGE_MODE){System.out.printf("\nyour calced attack: %d\nyour ev: %d\nyour base attack: %d\nopp hp: %d\nopp defense: %d\ndamage: %d\nroll: %f\nmove name: %s\n",stat,EV,baseStat,opp.HPStat,defenderStat,damage,currentRoll,move.name);}
                if(damage >= opp.HPStat){
                    EVrolls[index] = EV;
                    break;
                }
            }
            index++;
        }
        return EVrolls;
    }

    public static int calcHP(double EV, double level, double baseHP){return (int)((int)(((2*baseHP+31+(EV/4))*level)/100)+level+10);}

    //other factors
    private static double other(EVCalculatorUI.CurrentPokemon attacker, EVCalculatorUI.CurrentPokemon defender, double total, Move move, boolean spread, String weather){
        total*=getMatchups(defender.base.types,move.type);
        total*=STAB(attacker,move);
        total*= Items.getItemEffect(attacker.item, move.moveCategory);
        total*=getWeatherMultiplier(move, defender.base.types, weather);
        total*=getAbilityMultiplierAttacker();
        if(spread){total*=0.75;}

        if(Constants.DEBUG_DAMAGE_MODE){System.out.println("type "+getMatchups(defender.base.types,move.type)+"\nSTAB: "+STAB(attacker,move)+"\nItem: "+Items.getItemEffect(attacker.item,move.moveCategory)+"\nWeather: "+getWeatherMultiplier(move, defender.base.types, weather));}

        return total;
    }

    private static double getAbilityMultiplierAttacker(){return 1;}

    //returns a modifier for the damage
    private static double abilityDamageModifier(EVCalculatorUI.CurrentPokemon currentPokemon, Move move, String ability, boolean isAttacking){
        final String type = move.type;
        //isAttacking refers to if the attacking pokemon has this ability. so if its true then put all the positive modifiers like guts in here, and false for things like thick fat

        if(isAttacking){
            return switch(ability){
                case "Guts"->{if(!currentPokemon.status.equals("None")){yield 1.5;}else{yield 1;}}
                case "Iron Fist"->{if(move.moveCategory.equals(Constants.MOVE_CATS.Physical)){yield 1.2;}else{yield 1;}}
                case "Tough Claws"->{if(move.moveCategory.equals(Constants.MOVE_CATS.Physical)){yield 1.3;}else{yield 1;}}
                
                default -> 1;
            };
        }else{
            return switch(ability){
                case "Thick Fat"->{if(type.equals("Fire")||type.equals("Ice")){yield 0.5;}else{yield 1;}}
                case "Marvel Scale"->{if(!currentPokemon.status.equals("None")){yield 0.5;}else{yield 1;}}
                case "Heatproof"->{if(type.equals("Fire")){yield 0.5;}else{yield 1;}}
                case "Dry Skin"->{if(type.equals("Fire")){yield 1.25;}else if(type.equals("Water")) {yield 0;}else{yield 1;}}


                default -> 1;
            };
        }
    }

    private static double STAB(EVCalculatorUI.CurrentPokemon you, Move move){
            if(containsType(you.base.types, getType(move.type))){
                if(you.ability.equals("Adaptability")){return 2;}
                else{return 1.5;}
            }else{
                return 1;
            }
        }

    //all of this is shit code i made on the editor on the github website and its basically the equivalent of using google docs as an ide
    //so this code is proly shit and bad so fix it all later 
    private static double getWeatherMultiplier(Move move, Type[] opp, String weather){
        if(weather.equals("Sun")){
            return switch(move.type){
                case "Fire" -> 1.5;
                case "Water" -> 0.5;
                default -> 1;
            };

        }else if(weather.equals("Rain")){
            return switch(move.type){
                case "Water" -> 1.5;
                case "Fire" -> 0.5;
                default -> 1;
            };
            
        }else if(weather.equals("Sand")&&containsType(opp, getType("Rock"))&&move.moveCategory==Constants.MOVE_CATS.Special){return 0.5; //rocks get spdef boost

        }else if(weather.equals("Snow")&&containsType(opp, getType("Ice"))&&move.moveCategory==Constants.MOVE_CATS.Physical){return 0.5;}

        return 1;
    }
    
    private static boolean containsType(Type[] type, Type target){  
        Type secondType = type[0];
        if(type.length!=1){secondType=type[1];} //u have to do this because some pokemon arent dual type so u use the first type as a fallback
        return type[0]==target||secondType==target;
    }

    //this is shit and a placeholder sorry
    //takes calced speed not base speed
    public static void findSpeedPercentage(int speedStat){
        final Pokemon[] viableMons = getViablePokemonList();
        final int total = viableMons.length*2; //*2 because +speed also exists
        int outsped = 0;

        for(Pokemon currentMon:viableMons){
            int oppPlusSpeed = statCalculation(currentMon.baseSpeed,31,252,1.1,100,0);
            int oppNeutral = statCalculation(currentMon.baseSpeed,31,252,1,100,0);

            if(speedStat>oppPlusSpeed){outsped = outsped+2;
            }else if(speedStat>oppNeutral){outsped++;}
        }
        double percentage = ((double)outsped/total)*100;
        System.out.println("\nyour speed stat: "+speedStat+"total viable pokemon: "+total+"\noutsped: "+outsped+"\npercentage: "+percentage+"%");
    }
}
