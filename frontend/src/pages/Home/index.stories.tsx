import Home from '@/pages/Home/index';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/Home',
	component: Home,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <Home {...args} />;

export const DefaultHome = Template.bind({});
