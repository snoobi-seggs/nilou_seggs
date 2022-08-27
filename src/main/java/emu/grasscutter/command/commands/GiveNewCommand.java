package emu.grasscutter.command.commands;

import emu.grasscutter.GameConstants;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.GameDepot;
import emu.grasscutter.data.excels.AvatarData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.data.excels.ReliquaryAffixData;
import emu.grasscutter.data.excels.ReliquaryMainPropData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.props.ActionReason;
import emu.grasscutter.game.props.FightProperty;
import emu.grasscutter.utils.SparseSet;
import emu.grasscutter.server.packet.send.PacketAvatarEquipChangeNotify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Command(
    label = "givenew",
    aliases = {"unlocknew", "un", "new","gn"},
    usage = {"use and get all new items in this beta only, coz why use giveall and clutter"},
    permission = "player.give",
    permissionTargeted = "player.give.others",
    threading = true)
public final class GiveNewCommand implements CommandHandler {
    @Override public void execute(Player sender, Player targetPlayer, List<String> args) {
		if (targetPlayer == null){
			targetPlayer = sender;
		}
		
		//weaps
		List<GameItem> weaps = new ArrayList<>(6);
		
		GameItem nilouWeapon = new GameItem(11511);
		nilouWeapon.setLevel(90);
		nilouWeapon.setPromoteLevel(6);
		nilouWeapon.setTotalExp(9064450);
		nilouWeapon.setRefinement(4);
		weaps.add(nilouWeapon);
		
		GameItem cynoWeapon = new GameItem(13511);
		cynoWeapon.setLevel(90);
		cynoWeapon.setPromoteLevel(6);
		cynoWeapon.setTotalExp(9064450);
		cynoWeapon.setRefinement(4);
		weaps.add(cynoWeapon);
		
		GameItem candaceWeapon = new GameItem(13511);
		candaceWeapon.setLevel(90);
		candaceWeapon.setPromoteLevel(6);
		candaceWeapon.setTotalExp(9064450);
		candaceWeapon.setRefinement(4);
		weaps.add(candaceWeapon);
		
		GameItem shiftingWindblade = new GameItem(13419);
		shiftingWindblade.setLevel(90);
		shiftingWindblade.setPromoteLevel(6);
		shiftingWindblade.setTotalExp(6042650);
		shiftingWindblade.setRefinement(4);
		weaps.add(shiftingWindblade);
		
		GameItem maharaAquamarine = new GameItem(12415);
		maharaAquamarine.setLevel(90);
		maharaAquamarine.setPromoteLevel(6);
		maharaAquamarine.setTotalExp(6042650);
		maharaAquamarine.setRefinement(4);
		weaps.add(maharaAquamarine);
		
		GameItem xiphosMoonlight = new GameItem(11418);
		xiphosMoonlight.setLevel(90);
		xiphosMoonlight.setPromoteLevel(6);
		xiphosMoonlight.setTotalExp(6042650);
		xiphosMoonlight.setRefinement(4);
		weaps.add(xiphosMoonlight);
		
		GameItem wanderingEvenstar = new GameItem(14416);
		wanderingEvenstar.setLevel(90);
		wanderingEvenstar.setPromoteLevel(6);
		wanderingEvenstar.setTotalExp(6042650);
		wanderingEvenstar.setRefinement(4);
		weaps.add(wanderingEvenstar);
		
		targetPlayer.getInventory().addItems(weaps, ActionReason.SubfieldDrop);
		
		
		//avatars
		
		Avatar nilou = new Avatar(GameData.getAvatarDataMap().get(10000070));
        nilou.setLevel(90);
        nilou.setPromoteLevel(6);
        nilou.forceConstellationLevel(6);
        nilou.recalcStats();
		targetPlayer.addAvatar(nilou);
		nilou.equipItem(nilouWeapon,true);
		
		Avatar cyno = new Avatar(GameData.getAvatarDataMap().get(10000071));
        cyno.setLevel(90);
        cyno.setPromoteLevel(6);
        cyno.forceConstellationLevel(6);
        cyno.recalcStats();
		targetPlayer.addAvatar(cyno);
		cyno.equipItem(cynoWeapon,true);
		
		Avatar candace = new Avatar(GameData.getAvatarDataMap().get(10000072));
        candace.setLevel(90);
        candace.setPromoteLevel(6);
        candace.forceConstellationLevel(6);
        candace.recalcStats();
		targetPlayer.addAvatar(candace);
		candace.equipItem(candaceWeapon,true);
		

		//artifact TODO since i lazy
		/*List<GameItem> artifacts = new ArrayList<>(10);
		
		GameItem arti = new GameItem(21543);
		arti.setLevel(20);
		arti.setTotalExp(270475);
		arti.setMainPropId(14001);
		arti.getAppendPropIdList().clear();
		List<Integer> substat = new ArrayList<>(Arrays.asList(998002, 998002, 998002, 998002, 998002, 998002, 998002, 998002, 998002, 998002, 998002, 998002, 998002, 997002, 101061, 998007, 998007, 501223, 201201, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241, 301241));
		arti.getAppendPropIdList().addAll(substat);
		artifacts.add(arti);
		
		targetPlayer.getInventory().addItems(weaps, ActionReason.SubfieldDrop);
		//nilou.equipItem(arti,true);*/
		
	}
}