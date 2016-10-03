package subaraki.telepads.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import subaraki.telepads.item.TelepadItems;
import subaraki.telepads.mod.Telepads;
import subaraki.telepads.tileentity.TileEntityTelepad;

public class BlockTelepad extends Block{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);

	public BlockTelepad() {
		super(Material.GLASS);
		setLightLevel(0.2f);
		setHardness(5f);
		setSoundType(SoundType.GLASS);
		setCreativeTab(CreativeTabs.TRANSPORTATION);
		setHarvestLevel("pickaxe", 1);
		setUnlocalizedName(Telepads.MODID+".telepad");
		setRegistryName(Telepads.MODID+".telepad");
	}

	/////////////////rendering//////////////
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	///////////////TE Stuff//////////////////////
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityTelepad();
	}

	//////////Interaction///////

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {

		if(heldItem != null){
			Item item = heldItem.getItem();
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof TileEntityTelepad){
				TileEntityTelepad tet = (TileEntityTelepad)te;
				if(item != null){
					if(item.equals(TelepadItems.transmitter)){
						if(!tet.hasDimensionUpgrade()){
							tet.addDimensionUpgrade(true);
							tet.markDirty();
							world.notifyBlockUpdate(pos, getDefaultState(), getDefaultState(), 3);
						}
					}
					if(item.equals(TelepadItems.toggler)){
						if(!tet.hasRedstoneUpgrade()){
							tet.addRedstoneUpgrade();
							tet.markDirty();
							world.notifyBlockUpdate(pos, getDefaultState(), getDefaultState(), 3);
						}
					}
				}
			}
		}else{
			if(player.isSneaking()){
				//TODO add pad to player data list
			}
		}

		return super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
	}






	///////////////inherited methods////////////////////
	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if (entity instanceof EntityPlayer)
			return true;
		return false;
	}
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		//keep empty
	}
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 0;
	}
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		return 0;
	}
	@Override
	public boolean isVisuallyOpaque() {
		return true;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}
	@Override
	public float getExplosionResistance(Entity exploder) {
		return Float.MAX_VALUE;
	}
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return Float.MAX_VALUE;
	}
}
