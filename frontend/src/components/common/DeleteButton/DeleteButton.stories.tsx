import { Meta, Story } from '@storybook/react';
import DeleteButton from '@/components/common/DeleteButton/DeleteButton';

export default {
	title: 'common/DeleteButton',
	component: DeleteButton,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <DeleteButton {...args} />;

export const DefaultDeleteButton = Template.bind({});
DefaultDeleteButton.args = {
	size: '2rem',
};

export const DisabledDeleteButton = Template.bind({});
DisabledDeleteButton.args = {
	size: '2rem',
	disabled: true,
};
