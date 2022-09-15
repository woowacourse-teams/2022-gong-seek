import ErrorHandling from '@/pages/ServerErrorHandling';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Common/ErrorHandling',
	component: ErrorHandling,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <ErrorHandling {...args} />;

export const Default = Template.bind({});
Default.args = {};
