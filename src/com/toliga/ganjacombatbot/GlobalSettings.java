/*
Ganja Combat Bot is a fully or semi autonomous bot for the game RuneScape.
Copyright (C) 2016  Tolga Üstünkök

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.toliga.ganjacombatbot;

import com.toliga.ganjabots.path.PathProfile;
import org.dreambot.api.methods.map.Tile;

public class GlobalSettings {
    public static String[] MOB_NAMES = null;
    public static String[] LOOT_NAMES = null;
    public static String[] FOOD_NAMES = null;

    public static int HEALTH_PERCENT = 0;
    public static int FOOD_AMOUNT = 0;
    public static int WORLD_HOP_TIME = 0;
    public static int BREAK_TIME = 0;
    public static int WORK_TIME = 0;

    public static boolean POWERKILL = false;
    public static boolean LOOT = false;
    public static boolean EAT_FOOD = false;
    public static boolean USE_POTION = false;
    public static boolean USE_SPECIAL_ATTACK = false;
    public static boolean BANK_WHEN_FULL = false;
    public static boolean LOGOUT_WHEN_FULL = false;
    public static boolean BURY_BONES = false;
    public static boolean COMBAT_POTION = false;
    public static boolean STRENGTH_POTION = false;
    public static boolean DEFENCE_POTION = false;
    public static boolean ATTACK_POTION = false;
    public static boolean SUPER_COMBAT_POTION = false;
    public static boolean SUPER_STRENGTH_POTION = false;
    public static boolean SUPER_DEFENCE_POTION = false;
    public static boolean SUPER_ATTACK_POTION = false;
    public static boolean USE_ANTIBAN = false;
    public static boolean USE_PATH_CREATOR = false; // real value false
    public static boolean DEBUG = true; // default value will be false
    public static boolean EAT_FOOD_TAKE_LOOT = true;
    public static boolean WORLD_HOP = false;
    public static boolean INTERACTION_RESPONSE = false;
    public static boolean RANDOM_LOGOUT = false;

    public static PathProfile CHOSEN_BANK_GO_PROFILE = null;
    public static PathProfile CHOSEN_BANK_RETURN_PROFILE = null;

    public static Tile SOURCE_TILE = null;
}
