package com.jerryio.protocol_diagram.diagram.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.diagram.Field;

public class SplitService {

	private final Map<List<Field>, Map<Field, List<Field>>> memorization;
	private final Configuration config;

	public SplitService(Configuration config) {
		this.memorization = new HashMap<List<Field>,Map<Field,List<Field>>>();
		this.config = config;
	}

	public Map<Field, List<Field>> split(final List<Field> fields) {
		// return memorized value
		if (this.memorization.containsKey(fields)) {
			return this.memorization.get(fields);
		}

		final int canvasWidth = (int) this.config.getValue("bit");
		final Map<Field, List<Field>> ret = new HashMap<>();

		for (int i = 0, length = 0; i < fields.size(); i++) {
			final Field cur = fields.get(i);
			int remain = cur.getLength();

			ret.put(cur, new ArrayList<>());

			while (length + remain > canvasWidth) {
				ret.get(cur).add(new Field(
					cur.getName(),
					canvasWidth - length
				));

				remain -= canvasWidth - length;
				length = 0;
			}

			ret.get(cur).add(new Field(
					cur.getName(),
					remain
			));

			length = (length + remain) % canvasWidth;
		}

		// set memorization
		this.memorization.put(fields, ret);

		return ret;
	}

}
