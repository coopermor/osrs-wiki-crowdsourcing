package com.Crowdsourcing.statchange;

import com.Crowdsourcing.CrowdsourcingManager;
import com.google.common.collect.ImmutableSet;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.StatChanged;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.util.Text;

@Slf4j
public class CrowdsourcingStatChange
{
	@Inject
	private CrowdsourcingManager manager;

	@Inject
	private Client client;

	private int currentTick = -1;

	private final ImmutableSet<Skill> skills = ImmutableSet.copyOf(EnumSet.allOf(Skill.class));

	private final Set<Integer> ITEM_IDS = Set.of(
		ItemID._4DOSE1MAGIC,
		ItemID._4DOSEPOTIONOFSARADOMIN
	);

	private final Set<String> ACTIONS = Set.of("Eat", "Drink");

	private final HashMap<Skill, Integer> previousSkills = new HashMap<>();

	@SuppressWarnings("unused")
	@Subscribe
	private void onMenuOptionClicked(final MenuOptionClicked event)
	{
		int itemId = event.getMenuEntry().getItemId();
		if (!ITEM_IDS.contains(itemId))
		{
			return;
		}
		String action = event.getMenuEntry().getOption();
		if (!ACTIONS.stream().anyMatch(action::contains))
		{
			return;
		}

		for (Skill s : skills)
		{
			previousSkills.put(s, client.getBoostedSkillLevel(s));
		}
		currentTick = client.getTickCount();
		StatChangeItem data = new StatChangeItem(currentTick, itemId);
		manager.storeEvent(data);
	}

	@SuppressWarnings("unused")
	@Subscribe
	private void onStatChanged(final StatChanged event)
	{
		Skill skill = event.getSkill();

		if (currentTick == client.getTickCount())
		{
			if (client.getBoostedSkillLevel(skill) == previousSkills.get(skill))
			{
				return;
			}
			StatChangeData data = new StatChangeData(
				currentTick,
				skill,
				client.getRealSkillLevel(skill),
				previousSkills.get(skill),
				client.getBoostedSkillLevel(skill)
			);
			manager.storeEvent(data);
		}
	}
}
