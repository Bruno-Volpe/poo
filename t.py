def calculate_score(rolls):
    score = 0
    frame_index = 0
    frames = []

    for frame in range(10):
        if is_strike(rolls, frame_index):  # Strike
            bonus = strike_bonus(rolls, frame_index)
            score += 10 + bonus
            frames.append((10, bonus))
            frame_index += 1
        elif is_spare(rolls, frame_index):  # Spare
            bonus = spare_bonus(rolls, frame_index)
            score += 10 + bonus
            frames.append((rolls[frame_index], rolls[frame_index + 1], bonus))
            frame_index += 2
        else:  # Normal frame
            frame_score = rolls[frame_index] + rolls[frame_index + 1]
            score += frame_score
            frames.append((rolls[frame_index], rolls[frame_index + 1]))
            frame_index += 2

    return score, frames

def is_strike(rolls, frame_index):
    return rolls[frame_index] == 10

def strike_bonus(rolls, frame_index):
    return rolls[frame_index + 1] + rolls[frame_index + 2]

def is_spare(rolls, frame_index):
    return rolls[frame_index] + rolls[frame_index + 1] == 10

def spare_bonus(rolls, frame_index):
    return rolls[frame_index + 2]

def main():
    # Example input: number of pins knocked down per roll
    rolls = [10, 9, 1, 5, 5, 7, 2, 10, 10, 10, 2, 3, 6, 4, 7, 3, 3]

    score, frames = calculate_score(rolls)
    
    print("Sequência de pinos derrubados por frame:")
    for i, frame in enumerate(frames):
        if len(frame) == 3:
            print(f"Frame {i + 1}: {frame[0]} / {frame[1]} (bônus: {frame[2]})")
        else:
            print(f"Frame {i + 1}: {frame[0]} / {frame[1]}")

    print(f"Pontuação final: {score}")

if __name__ == "__main__":
    main()
