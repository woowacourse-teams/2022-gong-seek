import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { Story, Meta } from '@storybook/react';

export default {
	title: 'layout/PageLayout',
	component: PageLayout,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = () => <PageLayout />;

export const DefaultPageLayout = Template.bind({});
