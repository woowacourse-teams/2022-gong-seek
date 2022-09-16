import Header from '@/components/layout/Header/Header';
import { Story, Meta } from '@storybook/react';

export default {
	title: 'Layout/Header',
	component: Header,
	decorators: [(Story) => <Story />],
} as Meta;

const Template: Story = (args) => <Header {...args} />;

export const DefaultHeader = Template.bind({});
DefaultHeader.args = {};
