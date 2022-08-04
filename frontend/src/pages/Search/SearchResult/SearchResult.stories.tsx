import SearchResult from '@/pages/Search/SearchResult/SearchResult';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/SearchResult',
	component: SearchResult ,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<{target: string}> = (args) => <SearchResult  {...args} />;

export const DefaultSearchResult = Template.bind({});
DefaultSearchResult.args = {
    target: '검색 예시',
};
