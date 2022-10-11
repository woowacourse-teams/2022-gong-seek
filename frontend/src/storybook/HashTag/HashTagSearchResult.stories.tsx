import HashTagSearchResult, {
	HashTagSearchResultProps,
} from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'HashTag/HashTagSearchResult',
	component: HashTagSearchResult,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<HashTagSearchResultProps> = (args) => <HashTagSearchResult {...args} />;

export const DefaultHashTagResult = Template.bind({});
DefaultHashTagResult.args = {
	hashTags: ['12', '23', '33'],
};
