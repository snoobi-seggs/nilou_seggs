package emu.grasscutter.command.commands;

import java.util.List;

import com.mchange.v1.util.ArrayUtils;

import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.GadgetData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.entity.EntityAvatar;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.inventory.MaterialType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.game.world.Scene;
import emu.grasscutter.server.packet.send.PacketAvatarChangeCostumeNotify;

@Command(label = "equip", usage = {"equip"}, aliases = {"use","eq","e"}, permission = "player.equip", permissionTargeted = "player.equip.others")
public final class EquipCommand implements CommandHandler {

    @Override
    public void execute(Player sender, Player targetPlayer, List<String> args) {

        int item;
        int userid = 1;

        switch (args.size()) {
            case 2:
                try {
                    userid = Integer.parseInt(args.get(1));
                } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException ignored) {
                    CommandHandler.sendMessage(sender, "No ID User");
                    return;
                }
            case 1:
                try {
                    item = Integer.parseInt(args.get(0));
                } catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException ignored) {
                    CommandHandler.sendMessage(sender, "No ID Item");
                    return;
                }
                break;
            default:
                CommandHandler.sendMessage(sender, "/equip [id-item]");
                return;
        }

        ItemData itemData = GameData.getItemDataMap().get(item);
        GadgetData gadgetData = GameData.getGadgetDataMap().get(item);

        if (gadgetData == null && itemData == null) {
            CommandHandler.sendMessage(sender, "Item or gadget data not found");
            return;
        }

        // Use current character for modification
        EntityAvatar entity_current_avatar = sender.getTeamManager().getCurrentAvatarEntity();
        if (entity_current_avatar == null) {
            CommandHandler.sendMessage(sender, "No EntityAvatar");
            return;
        }
        Avatar current_avatar = entity_current_avatar.getAvatar();
        if (current_avatar == null) {
            CommandHandler.sendMessage(sender, "No getAvatar");
            return;
        }

        // Use current weapon for modification
        GameItem current_weapon = current_avatar.getWeapon();
        if (current_weapon == null) {
            CommandHandler.sendMessage(sender, "No getWeapon");
            return;
        }

        // Use current scene for modification
        Scene scene = targetPlayer.getScene();
        if (scene == null) {
            CommandHandler.sendMessage(sender, "No scene");
            return;
        }

        // ID ITEM
        int item_id_current = 0;
        int item_id_set = 0;
        String item_type = "Unknown";
        boolean need_update = false;

        // if have itemData found
        if (itemData != null) {

            // Modified Here

            // Change costume
            // 340000 : Summertime Sparkle
            // 340001 : Sea Breeze Dandelion
            if (itemData.getMaterialType() == MaterialType.MATERIAL_COSTUME) {

                // get id costume real avtar
                item_id_set = ArrayUtils.indexOf(itemData.getItemUse().get(0).getUseParam(), 0);//Integer.parseInt(itemData.getItemUse().get(0).getUseParam());

                item_type = "Costume";
                item_id_current = current_avatar.getCostume();
                if (item_id_current == 0) {
                    item_id_current = current_avatar.getAvatarId();
                }

                // edit here
                current_avatar.setCostume(item_id_set);

                // Save nope
                // current_avatar.save();

                need_update = true;
            }

            // 12504 : The Unforged
            // 15508 : Aqua Simulacra
            if (itemData.getItemType() == ItemType.ITEM_WEAPON) {

                item_type = "Weapon";
                item_id_current = current_weapon.getItemId();
                item_id_set = itemData.getId();
                need_update = true;

                // TODO: Edit Character style if different weapon type
                var useit = itemData.getItemType();
                var nowit = current_weapon.getItemData().getItemType();
                CommandHandler.sendMessage(sender, "Change " + nowit.toString() + " to " + useit.toString() + " ");
                if (useit != nowit) {
                    // IDK
                }

                // Simpel Rename ID WP
                current_weapon.setItemId(item_id_set);

                // Set Item Data (If you want to modify weapons here)
                current_weapon.setItemData(itemData);

                // Save,Because I don't want to destroy database, so don't save
                // current_weapon.save();

            }

        }

        // and if gadget set setGadgetId, actually it still doesn't work it looks like, there is something more that needs to be changed like a wp dump?
        // 50004074
        if (gadgetData != null) {

            if (itemData != null) {

                //itemData = current_weapon.getItemData();

                item_type = "Gadget";
                item_id_current = itemData.getGadgetId();
                item_id_set = gadgetData.getId();

                //itemData.setGadgetId(item_id_set);
                //current_weapon.setItemData(itemData);

                CommandHandler.sendMessage(sender, "Change Gadget " + gadgetData.getJsonName());
                need_update = true;
                
            }

        }

        // Foce Update Entity
        if (need_update) {

            // get entity avatar edit
            EntityAvatar entity = current_avatar.getAsEntity();

            // edit entity here or debug check here
            if (item_type == "Gadget") {

                // TODO
                CommandHandler.sendMessage(sender, "TES: " + entity.getSceneAvatarInfo().getWeapon().getGadgetId());

            }

            // update it
            scene.broadcastPacket(new PacketAvatarChangeCostumeNotify(entity));
        }

        if (item_id_current != 0) {
            CommandHandler.sendMessage(sender, "Succeed Replace " + item_type + " (Entity: " + need_update + ") " + item_id_current + " to " + item_id_set);
        } else {
            CommandHandler.sendMessage(sender, "Failed Replace " + item_type + " (Entity: " + need_update + ") " + item_id_current + " to " + item_id_set);
        }

    }
}