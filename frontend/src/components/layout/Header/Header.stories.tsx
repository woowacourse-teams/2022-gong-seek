import Header from '@/components/layout/Header/Header';
import { Story, Meta } from '@storybook/react';

export default {
	title: 'layout/Header',
	component: Header,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <Header {...args} />;

export const DefaultTabBar = Template.bind({});
DefaultTabBar.args = {};
