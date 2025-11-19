package com.Crowdsourcing.statchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.runelite.api.Skill;

@Data
@AllArgsConstructor
public class StatChangeData
{
	private final int gameTick;
	private final Skill skill;
	private final int baseLevel;
	private final int oldLevel;
	private final int newLevel;
}
