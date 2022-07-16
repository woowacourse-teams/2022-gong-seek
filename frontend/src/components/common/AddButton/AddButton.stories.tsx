import { Meta, Story } from '@storybook/react';
import AddButton from '@/components/common/AddButton/AddButton';

export default {
	title: 'common/AddButton',
	component: AddButton,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <AddButton {...args} />;

export const DefaultAddButton = Template.bind({});
DefaultAddButton.args = {
	size: '2rem',
};

export const DisabledAddButton = Template.bind({});
DisabledAddButton.args = {
	size: '2rem',
	disabled: true,
};
