import Loading from '@/components/common/Loading/Loading';
import { Meta, Story } from '@storybook/react';

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
