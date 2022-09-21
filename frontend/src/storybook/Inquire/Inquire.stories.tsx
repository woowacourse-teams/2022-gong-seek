import InquirePage from '@/pages/Inquire';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'InquirePage/InquirePage',
	component: InquirePage,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <InquirePage {...args} />;

export const DefaultInquirePage = Template.bind({});
