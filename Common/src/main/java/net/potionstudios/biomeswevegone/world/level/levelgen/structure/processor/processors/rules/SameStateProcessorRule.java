package net.potionstudios.biomeswevegone.world.level.levelgen.structure.processor.processors.rules;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.Passthrough;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;

import javax.annotation.Nullable;

public class SameStateProcessorRule {
    public static final Passthrough DEFAULT_BLOCK_ENTITY_MODIFIER = Passthrough.INSTANCE;
    public static final Codec<SameStateProcessorRule> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            RuleTest.CODEC.fieldOf("input_predicate").forGetter(arg -> arg.inputPredicate),
                            RuleTest.CODEC.fieldOf("location_predicate").forGetter(arg -> arg.locPredicate),
                            PosRuleTest.CODEC.optionalFieldOf("position_predicate", PosAlwaysTrueTest.INSTANCE).forGetter(arg -> arg.posPredicate),
                            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("output_location").forGetter(arg -> arg.outputBlock),
                            RuleBlockEntityModifier.CODEC.optionalFieldOf("block_entity_modifier", DEFAULT_BLOCK_ENTITY_MODIFIER).forGetter(arg -> arg.blockEntityModifier)
                    )
                    .apply(instance, SameStateProcessorRule::new)
    );

    private final RuleTest inputPredicate;
    private final RuleTest locPredicate;
    private final PosRuleTest posPredicate;
    private final Block outputBlock;
    private final RuleBlockEntityModifier blockEntityModifier;

    public SameStateProcessorRule(RuleTest inputPredicate, RuleTest locPredicate, PosRuleTest posPredicate, Block outputBlock, RuleBlockEntityModifier blockEntityModifier) {
        this.inputPredicate = inputPredicate;
        this.locPredicate = locPredicate;
        this.posPredicate = posPredicate;
        this.outputBlock = outputBlock;
        this.blockEntityModifier = blockEntityModifier;
    }

    public SameStateProcessorRule(RuleTest inputPredicate, RuleTest locPredicate, Block outputBlock) {
        this(inputPredicate, locPredicate, PosAlwaysTrueTest.INSTANCE, outputBlock, DEFAULT_BLOCK_ENTITY_MODIFIER);
    }

    public SameStateProcessorRule(RuleTest inputPredicate, Block outputBlock) {
        this(inputPredicate, AlwaysTrueTest.INSTANCE, PosAlwaysTrueTest.INSTANCE, outputBlock, DEFAULT_BLOCK_ENTITY_MODIFIER);
    }

    public boolean test(BlockState inputState, BlockState existingState, BlockPos localPos, BlockPos relativePos, BlockPos structurePos, RandomSource random) {
        return  this.inputPredicate.test(inputState, random)
                && this.locPredicate.test(existingState, random)
                && this.posPredicate.test(localPos, relativePos, structurePos, random);
    }

    public Block getOutputBlock() {
        return outputBlock;
    }


    @Nullable
    public CompoundTag getOutputTag(RandomSource random, @Nullable CompoundTag tag) {
        return this.blockEntityModifier.apply(random, tag);
    }
}
