import TabBar from '@/components/layout/TabBar/TabBar';
import { Story, Meta } from '@storybook/react';

export default {
	title: 'Layout/TabBar',
	component: TabBar,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <TabBar {...args} />;

export const DefaultTabBar = Template.bind({});
DefaultTabBar.args = {};
