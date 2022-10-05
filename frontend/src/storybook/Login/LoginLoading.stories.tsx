import LoginLoading from '@/pages/Login/LoginLoading/LoginLoading';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Login/Loading',
	component: LoginLoading,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <LoginLoading {...args} />;

export const DefaultLoginLoading = Template.bind({});
DefaultLoginLoading.args = {};
