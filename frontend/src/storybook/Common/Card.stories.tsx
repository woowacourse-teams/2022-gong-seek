import { PropsWithStrictChildren } from 'gongseek-types';

import Card from '@/components/@common/Card/Card';
import { CardProps } from '@/types/card';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Common/Card',
	component: Card,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<PropsWithStrictChildren<CardProps>> = (args) => <Card {...args} />;

export const DefaultCard = Template.bind({});
DefaultCard.args = {
	cssObject: {
		width: '1000px',
		height: '600px',
	},
	children: <div></div>,
	media: {
		minWidth: '700px',
		width: '300px',
		height: '220px',
	},
	hasActiveAnimation: true,
};
