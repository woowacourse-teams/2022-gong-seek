import * as S from '@/components/@common/ProgressiveBar/ProgressiveBar.styles';

interface ProgressiveBarProps {
	percent: number;
	gradientColor: string;
	time: number;
	width: string;
	height: string;
}

const ProgressiveBar = ({ percent, gradientColor, time, width, height }: ProgressiveBarProps) => (
	<S.ProgressiveBarContainer width={width} height={height}>
		<S.ProgressiveBarContent
			percent={percent}
			gradientColor={gradientColor}
			time={time}
		></S.ProgressiveBarContent>
	</S.ProgressiveBarContainer>
);

export default ProgressiveBar;
