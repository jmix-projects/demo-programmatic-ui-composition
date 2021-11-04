package com.company.app.screen.topic;

import io.jmix.ui.screen.*;
import com.company.app.entity.Topic;

@UiController("Topic.browse")
@UiDescriptor("topic-browse.xml")
@LookupComponent("topicsTable")
public class TopicBrowse extends StandardLookup<Topic> {
}