package com.Crowdsourcing.statchange;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatChangeItem
{
	private final int gameTick;
	private final int itemId;
}
