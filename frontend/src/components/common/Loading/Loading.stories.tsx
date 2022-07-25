import { Meta, Story } from '@storybook/react';
import Loading from '@/components/common/Loading/Loading';

export default {
	title: 'common/Loading',
	component: Loading,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = () => <Loading />;

export const DefaultInput = Template.bind({});
