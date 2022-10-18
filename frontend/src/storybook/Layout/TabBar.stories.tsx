import TabBar from '@/components/@layout/TabBar/TabBar';
import { INITIAL_VIEWPORTS } from '@storybook/addon-viewport';
import { Story, Meta } from '@storybook/react';

export default {
	title: 'Layout/TabBar',
	component: TabBar,
	decorators: [(Story) => <Story />],
	parameters: {
		viewport: {
			viewports: INITIAL_VIEWPORTS,
			defaultViewport: 'iphone6',
		},
	},
} as Meta;

const Template: Story = (args) => <TabBar {...args} />;

export const DefaultTabBar = Template.bind({});
DefaultTabBar.args = {};
