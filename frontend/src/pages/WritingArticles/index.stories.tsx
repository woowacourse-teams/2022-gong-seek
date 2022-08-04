import WritingArticles from '@/pages/WritingArticles/index';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/WritingArticles',
	component: WritingArticles,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <WritingArticles {...args} />;

export const DefaultWritingArticles = Template.bind({});
DefaultWritingArticles.args = {};
