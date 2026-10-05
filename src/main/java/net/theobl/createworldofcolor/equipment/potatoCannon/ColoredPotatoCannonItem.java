package net.theobl.createworldofcolor.equipment.potatoCannon;

import com.simibubi.create.AllEnchantments;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class ColoredPotatoCannonItem extends PotatoCannonItem {
    public ColoredPotatoCannonItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if(enchantment.is(AllEnchantments.POTATO_RECOVERY)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }
}
