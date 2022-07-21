import { Meta, Story } from '@storybook/react';
import CategoryArticles from '@/pages/CategoryArticles/CategoryArticles';

export default {
	title: 'pages/CategoryArticles',
	component: CategoryArticles,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <CategoryArticles {...args} />;

export const DefaultCategoryArticles = Template.bind({});
DefaultCategoryArticles.args = {};
