import { BrowserRouter } from 'react-router-dom';

import HashTagSearchResult, {
	HashTagSearchResultProps,
} from '@/components/hashTag/HashTagSearchResult/HashTagSearchResult';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'HashTag/HashTagSearchResult',
	component: HashTagSearchResult,
	decorators: [
		(Story) => (
			<BrowserRouter>
				<div style={{ width: '320px' }}>
					<Story />
				</div>
			</BrowserRouter>
		),
	],
} as Meta;

const Template: Story<HashTagSearchResultProps> = (args) => <HashTagSearchResult {...args} />;

export const DefaultHashTagResult = Template.bind({});
DefaultHashTagResult.args = {
	hashTags: ['12', '23', '33'],
};
